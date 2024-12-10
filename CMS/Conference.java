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
    private List<Speaker> speakers = new ArrayList<>();
    private Notifications notifications;
    private List<String> attendedAttendees = new ArrayList<>();
    


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
        this.notifications = new Notifications();

    }

    //register an attendee
    public void registerAttendee(Attendee attendee) {
        boolean exists = attendees.stream()
                .anyMatch(existingAttendee -> existingAttendee.getAttendeeID().equals(attendee.getAttendeeID()));
    
        if (exists) {
            System.out.println("Attendee with ID " + attendee.getAttendeeID() + " is already registered.");
            return;
        }
        attendees.add(attendee);
        System.out.println("Attendee " + attendee.getName() + " registered successfully!");
        
        if (notificationsEnabled) {
            notifications.sendToAttendee(attendee, "Welcome " + attendee.getName() + " to the GAF-AI 2025 Conference!");
        }
    }

    //add a session to the conference
    public void addSession(Session session) {
        for (Session s : sessions) {
            if (s.getSessionID().equals(session.getSessionID())) {
                System.out.println("Session with ID " + session.getSessionID() + " already exists.");
                return;
            }
        }
        sessions.add(session);
        System.out.println("Session " + session.getName() + " added successfully!");
        
        if (notificationsEnabled) {
            notifications.sendToAll(attendees, "New session added: " + session.getName());
        }
    }

    //enable or disable notifications
    public void enableNotifications(boolean enable) {
        this.notificationsEnabled = enable;
        System.out.println("Notifications " + (enable ? "enabled" : "disabled") + " for the conference.");
    }

    //saves & loades attendees & sessions to a file
    public void saveAttendees() {
        PersistenceManager.saveToFile(attendees, "attendees.dat");
    }
    
    public void loadAttendees() {
        this.attendees = PersistenceManager.loadFromFile("attendees.dat");
    }

        
    public void saveSessions() {
        PersistenceManager.saveToFile(sessions, "sessions.dat");
    }
    
    public void loadSessions() {
        this.sessions = PersistenceManager.loadFromFile("sessions.dat");
    }
    
    public void saveData() {
    saveAttendees();
    saveSessions();
}
            
    //search functionality
        public List<Attendee> searchAttendees(String query) {
        return PersistenceManager.search(attendees, attendee -> 
            attendee.getName().toLowerCase().contains(query.toLowerCase()) || 
            attendee.getEmail().toLowerCase().contains(query.toLowerCase())
        );
    }
    
    public List<Session> searchSessions(String query) {
        return PersistenceManager.search(sessions, session -> 
            session.getName().toLowerCase().contains(query.toLowerCase()) || 
            session.getRoom().toLowerCase().contains(query.toLowerCase())
        );
    }
    
    public List<Speaker> searchSpeakers(String query) {
        return PersistenceManager.search(speakers, speaker -> 
            speaker.getName().toLowerCase().contains(query.toLowerCase()) || 
            speaker.getBio().toLowerCase().contains(query.toLowerCase())
        );
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
        if (sessionID == null || sessionID.trim().isEmpty()) return null;
        for (Session session : sessions) {
            if (session.getSessionID().equalsIgnoreCase(sessionID.trim())) {
                return session;
            }
        }
        return null; //Return null if not found
    }

    
    public Attendee findAttendeeByID(String attendeeID) {
    for (Attendee attendee : attendees) {
        if (attendee.getAttendeeID().equals(attendeeID)) {
            return attendee; //Return the attendee if ID matches
        }
    }
    return null; //Return null if no match found
    }

    
    public Attendee findAttendeeByEmail(String email) {
        for (Attendee attendee : attendees) {
            if (attendee.getEmail().equals(email)) {
                return attendee;
            }
    }
    return null; //Return null if not found
    }

public void generateReport() {
    StringBuilder report = new StringBuilder();
    report.append("Conference Report: ").append(name).append("\n");
    report.append("Conference ID: ").append(conferenceID).append("\n");
    report.append("Dates: ").append(startDate).append(" - ").append(endDate).append("\n\n");

    //Add attendee summary
    report.append("Total Attendees: ").append(attendees.size()).append("\n");
    report.append("Attendee Details:\n");
    for (Attendee attendee : attendees) {
        report.append("- ").append(attendee.getName()).append(" (").append(attendee.getEmail()).append(")\n");
    }
    report.append("\n");

    // Add session summary
    report.append("Total Sessions: ").append(sessions.size()).append("\n");
    report.append("Session Details:\n");
    for (Session session : sessions) {
        report.append("- ").append(session.getName())
              .append(" (Room: ").append(session.getRoom()).append(")\n")
              .append("  Attendees: ").append(session.getAttendees().size()).append("/").append(session.getMaxAttendees()).append("\n");

        //Include feedback details for the session
        if (!session.getFeedbackList().isEmpty()) {
            report.append("  Feedback:\n");
            for (Feedback feedback : session.getFeedbackList()) {
                report.append("    - ").append(feedback.getAttendeeID())
                      .append(": ").append(feedback.getRating())
                      .append(" stars - ").append(feedback.getComment())
                      .append("\n");
            }
        } else {
            report.append("  No feedback available for this session.\n");
        }
    }

    //Save report to file
    PersistenceManager.saveTextToFile(report.toString(), "conference_report.txt");

    //Print report to console
    System.out.println(report.toString());
}


        
    public void issueCertificates(CertificateManager certificateManager) {
        for (Attendee attendee : attendees) {
            boolean hasAttended = sessions.stream()
                    .anyMatch(session -> session.getAttendedAttendees().contains(attendee.getAttendeeID()));
    
            if (!hasAttended) {
                System.out.println("No sessions attended by " + attendee.getName() + ". No certificate issued.");
                continue;
            }
    
            // Generate and send the certificate
            certificateManager.sendCertificateByEmail(attendee);
        }
    }


    public void submitFeedback(String attendeeID, String sessionID, int rating, String comment) {
        if (attendeeID == null || sessionID == null || comment == null || rating < 1 || rating > 5) {
            System.out.println("Invalid feedback input. Please check all fields.");
            return;
        }
        Session session = findSessionByID(sessionID);
        if (session == null) {
            System.out.println("Session not found for ID: " + sessionID);
            return;
        }
    
        Attendee attendee = findAttendeeByID(attendeeID);
        if (attendee == null) {
            System.out.println("Attendee not found for ID: " + attendeeID);
            return;
        }
    
        Feedback feedback = new Feedback("FB-" + System.currentTimeMillis(), attendeeID, sessionID, rating, comment);
        session.addFeedback(feedback);
        System.out.println("Feedback submitted for session: " + session.getName());
    }
    
    public void addSpeaker(Speaker speaker) {
    speakers.add(speaker);
    System.out.println("Speaker " + speaker.getName() + " added successfully.");
    }
    
    public Speaker findSpeakerByID(String speakerID) {
        for (Speaker speaker : speakers) {
            if (speaker.getSpeakerID().equals(speakerID)) {
                return speaker;
            }
        }
        return null;
    }
    
    public void assignSpeakerToSession(String sessionID, String speakerID) {
        Session session = findSessionByID(sessionID);
        Speaker speaker = findSpeakerByID(speakerID);
        if (session != null && speaker != null) {
            session.assignSpeaker(speaker);
            speaker.assignSession(session);
            System.out.println("Speaker " + speaker.getName() + " assigned to session " + session.getName());
        } else {
            System.out.println("Invalid session or speaker ID.");
        }
    }
    
    public void notifyAllAttendees(String message) {
        if (notificationsEnabled) {
            notifications.sendToAll(attendees, message);
        } else {
            System.out.println("Notifications are disabled. Enable notifications to send messages");
        }
    }

    //save the notifications log
    public void saveNotificationsLog(String fileName) {
        notifications.saveLogToFile(fileName);
    } 
    
    public void loadNotificationsLog(String fileName) {
    notifications.loadLogFromFile(fileName);
    }
    
public Attendee logInAttendee(String name, String email, String phoneNumber) {
    // Check if the attendee is already registered
    Attendee existingAttendee = findAttendeeByEmail(email);
    if (existingAttendee != null) {
        System.out.println("Welcome back, " + existingAttendee.getName() + "! Your attendee ID: " + existingAttendee.getAttendeeID());
        return existingAttendee;
    }

    // If not registered, dynamically create and register the attendee
    String attendeeID = "AT" + (attendees.size() + 1); // Generate a unique ID
    Attendee newAttendee = new Attendee(attendeeID, name, email, phoneNumber);
    registerAttendee(newAttendee); //Use the existing registerAttendee method

    // Send welcome email
    EmailService emailService = new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa");
    emailService.sendEmail(
        email,
        "Welcome to the Conference!",
        "Dear " + name + ",\n\nThank you for registering for the conference. Your attendee ID is: " + attendeeID + ".\n\nRegards,\nConference Team"
    );

    return newAttendee;
}
    
    public void displaySchedule() {
    System.out.println("Conference Schedule:");
    for (Session session : sessions) {
        System.out.println(session.getSessionDetails());
    }
    }
    
    public void displaySpeakers() {
    System.out.println("Conference Speakers:");
    for (Speaker speaker : speakers) {
        System.out.println(speaker.getBio());
        String sessionDetails = speaker.getSessionDetails();
        System.out.println(speaker.getSessionDetails());
        System.out.println(sessionDetails.isEmpty() ? speaker.getName() + " has no assigned sessions." : sessionDetails);

        System.out.println("-----------------------------------");
    }
    }

public String trackAttendanceWithFeedback(String sessionID, String attendeeID) {
    Session session = findSessionByID(sessionID);
    if (session != null) {
        Attendee attendee = findAttendeeByID(attendeeID);
        if (attendee != null) {
            //Mark attendance in the Session
            session.markAttendance(attendee);

            //Ensure attendees attendedSessions list is updated
            if (!attendee.getAttendedSessions().contains(sessionID)) {
                attendee.getAttendedSessions().add(sessionID); //Synchronize
            }

            // Send certificate
            CertificateManager certificateManager = new CertificateManager(
                "Certificate of Participation\n" +
                "----------------------------------\n" +
                "This certifies that {name} has participated in the session \"" + session.getName() + "\" on {date}.\n",
                new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa")
            );
            certificateManager.sendCertificateByEmail(attendee);

            return "Attendance marked for: " + attendee.getName() + " and certificate emailed.";
        } else {
            return "Attendee with ID: " + attendeeID + " not found.";
        }
    } else {
        return "Session with ID: " + sessionID + " not found.";
    }
}


public String getFormattedSessionsForGUI() {
    StringBuilder sessionDetails = new StringBuilder("Sessions:\n");
    for (Session session : sessions) {
        sessionDetails.append(session.getSessionID())
                      .append(": ").append(session.getName())
                      .append(" - Room: ").append(session.getRoom())
                      .append(" - Time: ").append(session.getTime())
                      .append("\n");
    }
    return sessionDetails.toString();
}

public String getSessionFeedbackForGUI(String sessionID) {
    Session session = findSessionByID(sessionID);
    if (session == null) return "Session not found.";
    return session.getFormattedFeedback();
}

}