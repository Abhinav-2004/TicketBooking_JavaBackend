package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Collection;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH= "app/src/main/java/ticket/booking/localdb/users.json";
    private static final String TRAINS_PATH= "app/src/main/java/ticket/booking/localdb/trains.json";
    private ObjectMapper objectMapper = new ObjectMapper();//using to map json to java variables

    public UserBookingService(User user1) throws IOException {
        this.user=user1;

        File users = new File(USERS_PATH);
        //Using Jackson Library by downloading from build.gradle to parse userID->user_id conversions automatically
        userList = objectMapper.readValue(users, new TypeReference<List<User>>(){});
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




        saveUserListToFile();

        return Boolean.FALSE;
    }

}
