package ticket.booking.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    @JsonProperty("ticket_id")
    private String ticketId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("date_of_travel")
    private String dateOfTravel;
    @JsonProperty("train")
    private Train train;
    @JsonProperty("ticket_info")
    private String tikcetInfo;

    public String getTikcetInfo() {
        return tikcetInfo;
    }

    public void setTikcetInfo(String tikcetInfo) {
        this.tikcetInfo = tikcetInfo;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }
}
