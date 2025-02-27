package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    @JsonProperty("name")
    private String name;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("password")
    private String password;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @JsonProperty("tickets_booked")
    private List<Ticket> ticketsBooked;

    public User(){}; // default constructor

    public User(String name,String password, String hashedPassword, List<Ticket> ticketsBooked,String userId) {
        this.name = name;
        this.userId = userId;
        this.password = password;

        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
    }

    public void printTicketsBooked() {
        for(int i=0; i<ticketsBooked.size();i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }
    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
