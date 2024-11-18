import java.util.*;

public class Main {
    public static void main(String[] args) {
        //step 1: create a conference
        Conference conference = new Conference("CONF001", "GAF-AI 2025", new Date(), new Date());

        //step 2: add sessions to the conference
        Session session1 = new Session("S001", "AI Innovations", new Date(), "10:00 AM", "Room A", 50);
        Session session2 = new Session("S002", "Future of Robotics", new Date(), "2:00 PM", "Room B", 30);
        conference.addSession(session1);
        conference.addSession(session2);

        // step 3: register attendees
        Attendee attendee1 = new Attendee("AT001", "hazem 1", "hazem1@example.com", "1234567890");
        Attendee attendee2 = new Attendee("AT002", "hazem 2", "hazem2@example.com", "0987654321");
        conference.registerAttendee(attendee1);
        conference.registerAttendee(attendee2);

        // step 4: save sessions and attendees to file
        try {
            PersistenceManager.saveToFile(conference.getSessions(), "sessions.dat");
            PersistenceManager.saveToFile(conference.getAttendees(), "attendees.dat");
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }

        // step 5: load sessions and attendees from file
        try {
            List<Session> loadedSessions = PersistenceManager.loadFromFile("sessions.dat");
            List<Attendee> loadedAttendees = PersistenceManager.loadFromFile("attendees.dat");

            System.out.println("\n--- Loaded Sessions ---");
            if (loadedSessions != null) {
                for (Session session : loadedSessions) {
                    System.out.println(session.getSessionDetails());
                }
            }

            System.out.println("\n--- Loaded Attendees ---");
            if (loadedAttendees != null) {
                for (Attendee attendee : loadedAttendees) {
                    System.out.println(attendee.getName() + " - " + attendee.getEmail());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}