import java.util.Date;

public class Notifications {
    private String message;
    private Date timestamp;
    
    public Notifications(String message) {
        this.message = message;
        this.timestamp = new Date(); //auto set the timestamp to the current date & time
    }

    //send notification to a specific attendee
    public void sendToAttendee(Attendee attendee) {
        System.out.println("Sending notification to: " + attendee.getName());
        System.out.println("Email: " + attendee.getEmail());
        System.out.println("Message: " + message);
        System.out.println("Sent on: " + timestamp);
    }

    //getters & setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
