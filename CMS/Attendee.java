import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Attendee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String attendeeID;
    private String name;
    private String email;
    private List<Session> schedule;
    private String phoneNumber;

    public Attendee(String attendeeID, String name, String email, String phoneNumber) {
        this.attendeeID = attendeeID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.schedule = new ArrayList<>(); //initialize schedule as an empty list
    }

    //add a session to the attendee's schedule
    public void createSchedule(Session session) {
        schedule.add(session);
        System.out.println("Session " + session.getName() + " added to " + name + "'s schedule.");
    }

    //simulate viewing a certificate
    public void viewCertificate() {
        System.out.println("Viewing certificate for: " + name);
        // Placeholder logic for certificate
    }

    //provide feedback for a session
    public void provideFeedback(String feedback, int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5.");
            return;
        }
        Feedback feedbackObj = new Feedback("FB-" + System.currentTimeMillis(), attendeeID, rating, feedback);
        System.out.println("Feedback submitted: " + feedbackObj.getFeedback());
    }

    //update personal information
    public void updatePersonalInfo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        System.out.println("Personal information updated for attendee: " + this.name);
    }

    //simulate downloading a certificate
    public void downloadCertificate() {
        System.out.println("Downloading certificate for: " + name);
        // Placeholder logic for certificate download
    }

    //getters & setters
    public String getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Session> getSchedule() {
        return schedule;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
