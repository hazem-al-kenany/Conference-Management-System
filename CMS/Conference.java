import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

public class Conference {
    private String conferenceID;
    private String name;
    private Date startDate;
    private Date endDate;
    private boolean notificationsEnabled;
    private List<Session> sessions; //stores all sessions in the conference
    private List<Attendee> attendees; //stores all attendees registered for the conference

    public Conference(String conferenceID, String name, Date startDate, Date endDate) {
        if (conferenceID == null || conferenceID.isEmpty())
            throw new IllegalArgumentException("Conference ID cannot be null or empty");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Conference name cannot be null or empty");

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

    //enable or disable notifications
    public void enableNotifications(boolean enable) {
        this.notificationsEnabled = enable;
        System.out.println("Notifications " + (enable ? "enabled" : "disabled") + " for the conference.");
    }

    //placeholder to generate a report
    public void generateReport() {
        System.out.println("Generating conference report...");
        //future implementation
    }

    //placeholder to issue certificates
    public void issueCertificates() {
        System.out.println("Issuing certificates to all attendees...");
        //future implementation
    }

    //save attendees to a file
    public void saveAttendees(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this.attendees);
            System.out.println("Attendees saved successfully.");
        }
    }

    // Load attendees from a file
    @SuppressWarnings("unchecked")
    public void loadAttendees(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.attendees = (List<Attendee>) ois.readObject();
            System.out.println("Attendees loaded successfully.");
        }
    }

    //save sessions to a file
    public void saveSessions(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this.sessions);
            System.out.println("Sessions saved successfully.");
        }
    }

    // Load sessions from a file
    @SuppressWarnings("unchecked")
    public void loadSessions(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.sessions = (List<Session>) ois.readObject();
            System.out.println("Sessions loaded successfully.");
        }
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
    
    //search for a session by ID
    public Session findSessionByID(String sessionID) {
    for (Session session : sessions) {
        if (session.getSessionID().equals(sessionID)) {
            return session;
        }
    }
    return null; //return null if not found
    }
    
    public Attendee findAttendeeByEmail(String email) {
    for (Attendee attendee : attendees) {
        if (attendee.getEmail().equals(email)) {
            return attendee;
        }
    }
    return null; // Return null if not found
}
}