import java.util.ArrayList;
import java.util.List;

public class Speaker extends Person {
    private String speakerID; //unique id for the speaker
    private String bio; //biography of the speaker
    private List<Session> assignedSessions; //sessions assigned to the speaker

    //constructor to initialize speaker details
    public Speaker(String speakerID, String name, String email, String bio) {
        super(name, email); //initialize name and email using parent class
        this.speakerID = speakerID;
        this.bio = bio;
        this.assignedSessions = new ArrayList<>(); //initialize empty session list
    }

    //assign a session to the speaker
    public void assignSession(Session session) {
        if (session == null) { //check if session is null
            System.out.println("Cannot assign a null session.");
            return;
        }
        System.out.println("Attempting to assign session " + session.getName() + " to speaker: " + getName());
        if (!assignedSessions.contains(session)) { //add session if not already assigned
            assignedSessions.add(session);
        } else {
            System.out.println("Session " + session.getName() + " is already assigned to speaker: " + getName());
        }
    }

    //get all sessions assigned to the speaker
    public List<Session> getAssignedSessions() {
        return assignedSessions;
    }

    //get speaker's biography
    public String getBio() {
        return "Speaker Name: " + getName() + "\nBiography: " + bio;
    }

    //get details of all assigned sessions
    public String getSessionDetails() {
        StringBuilder details = new StringBuilder("Assigned Sessions for " + getName() + ":\n");
        for (Session session : assignedSessions) { //iterate through all sessions
            details.append("- ").append(session.getName()) //append session name
                   .append(" (ID: ").append(session.getSessionID()).append(")\n"); //append session id
        }
        return details.toString();
    }

    //getters and setters
    public String getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }

    public String getBiography() {
        return bio;
    }

    public void setBiography(String bio) {
        this.bio = bio;
    }
}