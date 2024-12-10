import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Attendee extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String attendeeID; //unique id for the attendee
    private List<Session> schedule; //list of registered sessions
    private String phoneNumber; //phone number of the attendee
    private List<String> attendedSessions; //list of attended session ids

    //constructor to initialize attendee details
    public Attendee(String attendeeID, String name, String email, String phoneNumber) {
        super(name, email); //initialize name and email using parent class
        this.attendeeID = attendeeID;
        this.phoneNumber = phoneNumber;
        this.schedule = new ArrayList<>(); //initialize empty schedule
        this.attendedSessions = new ArrayList<>(); //initialize empty attendance list
    }

    //add a session to the schedule if not already added
    public void createSchedule(Session session) {
        if (schedule.contains(session)) { //check if session is already in schedule
            System.out.println("Session " + session.getName() + " is already in " + getName() + "'s schedule.");
            return;
        }
        if (session.registerAttendee(this)) { //register attendee in the session
            schedule.add(session); //add session to attendee's schedule
            System.out.println("Session " + session.getName() + " added to " + getName() + "'s schedule.");
        }
    }

    //view and email a certificate
    public void viewCertificate(CertificateManager certificateManager) {
        String certificatePath = certificateManager.generateAndSaveCertificate(this); //generate certificate
        System.out.println("Viewing certificate for: " + getName());
        System.out.println("Certificate file: " + certificatePath);
        certificateManager.sendCertificateByEmail(this); //email the certificate
        System.out.println("Certificate emailed to: " + getEmail());
    }

    //download a certificate
    public void downloadCertificate(CertificateManager certificateManager) {
        String certificatePath = certificateManager.generateAndSaveCertificate(this); //generate certificate
        System.out.println("Downloading certificate for: " + getName());
        System.out.println("Certificate downloaded: " + certificatePath);
    }

    //submit feedback for a session
    public void provideFeedback(String sessionID, String feedback, int rating, Conference conference) {
        conference.submitFeedback(attendeeID, sessionID, rating, feedback); //submit feedback to the conference
    }

    //update attendee's personal information
    public void updatePersonalInfo(String name, String email, String phoneNumber) {
        setName(name); //update name
        setEmail(email); //update email
        this.phoneNumber = phoneNumber; //update phone number
        System.out.println("Personal information updated for attendee: " + getName());
    }

    //mark attendance for a session
    public void attendSession(Session session) {
        if (!schedule.contains(session)) { //check if session is in schedule
            System.out.println("Cannot attend a session not in schedule: " + session.getName());
            return;
        }
        if (!attendedSessions.contains(session.getSessionID())) { //check if already attended
            attendedSessions.add(session.getSessionID()); //mark as attended
            System.out.println(getName() + " attended session: " + session.getName());
        } else {
            System.out.println(getName() + " has already attended session: " + session.getName());
        }
    }

    //get list of attended sessions
    public List<String> getAttendedSessions() {
        return attendedSessions;
    }

    //generate email content for a certificate
    public String getCertificateEmailContent(String conferenceName, String certificateFilePath) {
        return "Dear " + getName() + ",\n\n" +
               "Congratulations on attending the conference \"" + conferenceName + "\".\n" +
               "Attached is your certificate of participation.\n\n" +
               "Best regards,\nConference Team\n\nCertificate file: " + certificateFilePath;
    }

    //get schedule details for GUI display
    public String getScheduleForGUI() {
        StringBuilder scheduleDetails = new StringBuilder(); //store schedule details
        for (Session session : schedule) { //iterate through all sessions
            scheduleDetails.append(session.getName()) //append session name
                           .append(" - Room: ").append(session.getRoom()) //append room
                           .append(" - Time: ").append(session.getTime()) //append time
                           .append("\n");
        }
        return scheduleDetails.toString(); //return formatted schedule
    }

    //getters and setters
    public String getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
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