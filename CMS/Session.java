import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L; //unique ID for serialization

    private String sessionID;
    private String name;
    private Date date;
    private String time;
    private String room;
    private int maxAttendees;
    private List<Attendee> attendees; //list of attendees for the session

    // Constructor
    public Session(String sessionID, String name, Date date, String time, String room, int maxAttendees) {
        if (sessionID == null || sessionID.isEmpty())
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Session name cannot be null or empty");

        this.sessionID = sessionID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.room = room;
        this.maxAttendees = maxAttendees;
        this.attendees = new ArrayList<>();
    }

    //register an attendee
    public boolean registerAttendee(Attendee attendee) {
        if (attendees.size() < maxAttendees) {
            attendees.add(attendee);
            System.out.println("Attendee " + attendee.getName() + " registered for session: " + name);
            return true; //successfully registered
        } else {
            System.out.println("Session is full! Cannot register attendee: " + attendee.getName());
            return false; //registration failed
        }
    }

    //get session details
    public String getSessionDetails() {
        return "Session ID: " + sessionID + ", Name: " + name + ", Date: " + date + ", Time: " + time +
                ", Room: " + room + ", Max Attendees: " + maxAttendees + ", Registered: " + attendees.size();
    }

    //assign a room
    public void assignRoom(String room) {
        this.room = room;
        System.out.println("Room assigned to session " + name + ": " + room);
    }

    //check availability
    public boolean checkAvailability() {
        return attendees.size() < maxAttendees;
    }

    //getters & setters
    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    //save sessions to file
    public void saveSessions(List<Session> sessions, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(sessions);
            System.out.println("Sessions saved successfully.");
        }
    }

    //lLoad sessions from file
    public static List<Session> loadSessions(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Session>) ois.readObject();
        }
    }
}
