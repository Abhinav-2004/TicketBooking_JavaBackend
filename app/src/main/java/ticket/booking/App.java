/*
 * This source file was generated by the Gradle 'init' task
 */
package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {


    public static void main(String[] args) {
        System.out.println("Welcome to the ticket booking app");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try{
            userBookingService=new UserBookingService();
        }
        catch(IOException ex){
            System.out.println("There seems to be a problem");
            System.out.println(ex.getMessage());
            return ;
        }
        while(option != 7){
            System.out.println("Please enter your option");
            System.out.println("1.Sign Up");
            System.out.println("2.Sign In");
            System.out.println("3.Fetch Bookings");
            System.out.println("4.Search Trains");
            System.out.println("5.Book a Seat");
            System.out.println("6.Cancel My Booking");
            System.out.println("7.Exit");
            option=scanner.nextInt();
            Train trainSelectedForBooking=new Train();
            switch(option){
                case 1:
                    System.out.println("Please enter your Name");
                    String nameToSignUp = scanner.next();
                    System.out.println("Please enter your Password");
                    String passwordToSignUp = scanner.next();

                    User userToSignUp = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp),new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignUp);
                    break;

                    case 2:
                        System.out.println("Please enter your UserName");
                        String nameToSignIn = scanner.next();
                        System.out.println("Please enter your Password");
                        String passwordToSignIn = scanner.next();
                        User userToLogin = new User(nameToSignIn, passwordToSignIn, UserServiceUtil.hashPassword(passwordToSignIn),new ArrayList<>(), UUID.randomUUID().toString());
                        try{
                            userBookingService = new UserBookingService(userToLogin);
                        } catch (IOException e) {
                            return;
                        }
                        break;

                        case 3:
                            System.out.println("Fetch Bookings");
                            userBookingService.fetchBooking();
                            break;
                            case 4:
                                System.out.println("Search Trains");
                                System.out.println("Enter Source");
                                String source = scanner.next();
                                System.out.println("Enter Destination");
                                String destination = scanner.next();
                                try {
                                    List<Train> trains = userBookingService.getTrains(source, destination);
                                    int index=1;
                                    for(Train train: trains){
                                        System.out.println(index+"->Train Id - "+train.getTrainId());
                                        for(Map.Entry<String, String> entry : train.getStationTimes().entrySet()){
                                            System.out.println("Station - >"+entry.getKey()+" Time - >"+entry.getValue());
                                        }
                                        index++;
                                    }
                                    System.out.println("Select a train by typing 1,2,3....");
                                    trainSelectedForBooking = trains.get(scanner.nextInt());
                                    break;
                                } catch (IOException e) {
                                    System.out.println("There seems to be a problem loading the train list from source and destination");

                                }
                                break;
                                case 5:
                                    try {
                                        System.out.println("Select a seat out of these seats");
                                        List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                                        for (List<Integer> row: seats){
                                            for (Integer val: row){
                                                System.out.print(val+" ");
                                            }
                                            System.out.println();
                                        }
                                        System.out.println("Select the seat by typing the row and column");
                                        System.out.println("Enter the row");
                                        int row = scanner.nextInt();
                                        System.out.println("Enter the column");
                                        int col = scanner.nextInt();
                                        System.out.println("Booking your seat....");
                                        Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col);
                                        if(booked.equals(Boolean.TRUE)){
                                            System.out.println("Booked! Enjoy your journey");
                                        }else{
                                            System.out.println("Can't book this seat");
                                        }
                                        break;
                                    }
                                    catch(IOException ex){
                                        System.out.println("There seems to be a problem in fetching seats");
                                    }

            }
        }
    }
}
