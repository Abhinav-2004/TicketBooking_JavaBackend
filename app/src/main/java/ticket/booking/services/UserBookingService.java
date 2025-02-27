package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private List<Train> trainList;
    private static final String USERS_PATH= "app/src/main/java/ticket/booking/localdb/users.json";
    private static final String TRAINS_PATH= "app/src/main/java/ticket/booking/localdb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();//using to map json to java variables

    public UserBookingService() throws IOException {
        loadUser();
    }

    public UserBookingService(User user1) throws IOException {
        this.user=user1;

        loadUser();
    }

    public List<User> loadUser() throws IOException{

        File users = new File(USERS_PATH);
        //Using Jackson Library by downloading from build.gradle to parse userID->user_id conversions automatically
        userList = objectMapper.readValue(users, new TypeReference<List<User>>(){});
        return userList;
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }
        catch (IOException ex){
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
        //deserializing object to again json to save in file
    }

    public void fetchBooking(){
        user.printTicketsBooked();
    }

    public Boolean cancelBooking(String ticketId) throws IOException{
        //Implement the logic below
        List<Ticket> ticketList =  user.getTicketsBooked();
        ticketList.stream().filter(user1 -> user1.getTicketId().equals(ticketId)).findFirst().ifPresent(ticket -> {
           user.getTicketsBooked().remove(ticket);
        });
        saveUserListToFile();

        return Boolean.FALSE;
    }
    public List<Train> getTrains(String source, String destination) throws IOException {

        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrain(source, destination);
        }
        catch (IOException e){
            System.out.println("There seems to be a problem loading the list of trains using source and destination");
            return new ArrayList<>();
        }
    }
    public List<List<Integer>> fetchSeats(Train train) throws IOException {
        List<List<Integer>> seats = train.getSeats();
        return seats;
    }

    public boolean bookTrainSeat(Train trainSelectedForBooking, int row, int col){
        //Two steps
        //1->Mark seat as booked in train db
        //2->Create a ticket object and store in user db
        try{
            TrainService trainService=new TrainService();
            List<List<Integer>> seats = trainSelectedForBooking.getSeats();
            if(row>=0 && col>=0 && row<seats.size()&& col<seats.get(row).size()){
                if(seats.get(row).get(col)==0){
                    seats.get(row).set(col,1);
                    trainSelectedForBooking.setSeats(seats);
                    trainService.addTrain(trainSelectedForBooking);
                    return true;
                }
                else{
                    System.out.println("Seat already Booked");
                    return false;
                }
            }
            else{
                return false;
            }
        }
        catch(IOException e){
            System.out.println("Error while booking train seat");
            return Boolean.FALSE;
        }


    }

}
