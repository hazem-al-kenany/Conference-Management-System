import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Write a description of class Session here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Session
{
    private String sessionID;
    private String name;
    private Date date;
    private String time;
    private String room;
    private int maxAttendees;
    private List<Attendee> attendees; //list of attendees for the session
    
    /**
     * Constructor for objects of class Session
     */
    public Session(String sessionID, String name, Date date, String time, String room, int maxAttendees) {
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
}