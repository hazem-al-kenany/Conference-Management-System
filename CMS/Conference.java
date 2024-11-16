import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Write a description of class Conference here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class Conference
{
    // instance variables - replace the example below with your own
    private String conferenceID;
    private String name;
    private Date startDate;
    private Date endDate;
    private boolean notificationsEnabled;
    private List<Session> sessions; //stores all sessions in the conference
    private List<Attendee> attendees; //stores all attendees registered for the conference
    
    /**
     * Constructor for objects of class Conference
     */
    public Conference(String conferenceID, String name, Date startDate, Date endDate)
    {
        this.conferenceID = conferenceID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationsEnabled = false; //default is disabled
        this.sessions = new ArrayList<>();
        this.attendees = new ArrayList<>();
    }
    
    //register an attendee
    public void registerAttendee(Attendee attendee) {
        attendees.add(attendee);
        System.out.println("Attendee " + attendee.getName() + " registered successfully!");
    }

    //add a session to the conference
    public void addSession(Session session) {
        sessions.add(session);
        System.out.println("Session " + session.getName() + " added successfully!");
    }

    //enable notifications
    public void enableNotifications(boolean enable) {
        this.notificationsEnabled = enable;
        System.out.println("Notifications " + (enable ? "enabled" : "disabled") + " for the conference.");
    }

    //placeholder to generate a report
    public void generateReport() {
        System.out.println("Generating conference report...");
        // Future implementation
    }

    //placeholder to issue certificates
    public void issueCertificates() {
        System.out.println("Issuing certificates to all attendees...");
        // Future implementation
    }

    //getters & setters
    public String getConferenceID() {
        return conferenceID;
    }

    public void setConferenceID(String conferenceID) {
        this.conferenceID = conferenceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }
}
