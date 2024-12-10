import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionID; //unique id for the session
    private String name; //session name
    private Date date; //session date
    private String time; //session time
    private String room; //room where the session is held
    private int maxAttendees; //maximum attendees allowed
    private List<Attendee> attendees; //list of attendees registered
    private List<Feedback> feedbackList; //list of feedback for this session
    private Speaker speaker; //assigned speaker for the session
    private List<String> attendedAttendees; //list of attendee IDs who attended

    //constructor to initialize session details
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
        this.feedbackList = new ArrayList<>();
        this.attendedAttendees = new ArrayList<>();
    }

    //register an attendee to the session
    public boolean registerAttendee(Attendee attendee) {
        if (attendees.stream().anyMatch(a -> a.getAttendeeID().equals(attendee.getAttendeeID()))) {
            System.out.println("Attendee " + attendee.getName() + " is already registered for this session.");
            return false; //prevent duplicate registration
        }
        if (attendees.size() < maxAttendees) {
            attendees.add(attendee);
            System.out.println("Attendee " + attendee.getName() + " registered for session: " + name);
            return true; //successful registration
        } else {
            System.out.println("Session is full! Cannot register attendee: " + attendee.getName());
            return false; //registration failed
        }
    }

    //add feedback for the session
    public void addFeedback(Feedback feedback) {
        boolean isRegistered = attendees.stream()
            .anyMatch(attendee -> attendee.getAttendeeID().equals(feedback.getAttendeeID()));

        if (!isRegistered) {
            System.out.println("Attendee is not registered for this session. Feedback not added.");
            return;
        }
        feedbackList.add(feedback);
        System.out.println("Feedback added for session: " + name);
    }

    //get formatted feedback details for the session
    public String getFormattedFeedback() {
        if (feedbackList.isEmpty()) {
            return "No feedback available for this session.";
        }
        StringBuilder feedbackDetails = new StringBuilder("Feedback for session: " + name + "\n");
        for (Feedback feedback : feedbackList) {
            feedbackDetails.append(feedback.getFeedback()).append("\n");
        }
        return feedbackDetails.toString();
    }

    //get details of the session
    public String getSessionDetails() {
        String speakerDetails = (speaker != null)
            ? "Speaker: " + speaker.getName() + " (" + speaker.getBio() + ")"
            : "No speaker assigned.";
        return "Session ID: " + sessionID + ", Name: " + name + ", Date: " + date + ", Time: " + time +
               ", Room: " + room + ", Max Attendees: " + maxAttendees + ", Registered: " + attendees.size() +
               "\n" + speakerDetails + "\n";
    }

    //assign a room to the session
    public void assignRoom(String room) {
        this.room = room;
        System.out.println("Room assigned to session " + name + ": " + room);
    }

    //check if the session has availability for more attendees
    public boolean checkAvailability() {
        return attendees.size() < maxAttendees;
    }

    //assign a speaker to the session
    public void assignSpeaker(Speaker speaker) {
        this.speaker = speaker;
        if (speaker != null) {
            speaker.assignSession(this); //add the session to the speaker's list
            System.out.println("Session " + name + " assigned to speaker: " + speaker.getName());
        } else {
            System.out.println("Speaker is null for session: " + name);
        }
    }

    //mark attendance for an attendee
    public void markAttendance(Attendee attendee) {
        if (!attendedAttendees.contains(attendee.getAttendeeID())) {
            attendedAttendees.add(attendee.getAttendeeID()); //add attendee ID to the attendance list
            System.out.println("Attendance marked for: " + attendee.getName() + " in session: " + name);
            
            //sync with attendee
            attendee.attendSession(this);

            //send certificate to attendee
            CertificateManager certificateManager = new CertificateManager(
                "Certificate of Participation\n" +
                "----------------------------------\n" +
                "This certifies that {name} has participated in the session \"" + name + "\" on {date}.\n",
                new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa")
            );
            certificateManager.sendCertificateByEmail(attendee);
        }
    }

    //get a list of attendee IDs who attended
    public List<String> getAttendedAttendees() {
        return attendedAttendees;
    }

    //notify all attendees of a session update
    public void notifyAttendees(EmailService emailService, String message) {
        for (Attendee attendee : attendees) {
            emailService.sendEmail(
                attendee.getEmail(),
                "Update for Session: " + name,
                "Dear " + attendee.getName() + ",\n\n" +
                message + "\n\nBest regards,\nConference Team"
            );
            System.out.println("Notification email sent to: " + attendee.getEmail());
        }
    }

    //send certificates to all attendees who attended
    public void sendCertificates(EmailService emailService, String conferenceName) {
        for (String attendeeID : attendedAttendees) {
            Attendee attendee = attendees.stream()
                .filter(a -> a.getAttendeeID().equals(attendeeID))
                .findFirst()
                .orElse(null);
            if (attendee != null) {
                String certificatePath = "certificate_" + attendee.getAttendeeID() + ".txt";
                emailService.sendEmailWithAttachment(
                    attendee.getEmail(),
                    "Certificate for " + attendee.getName(),
                    "Please find your certificate attached.",
                    certificatePath
                );
            }
        }
    }

    //get a formatted list of attendees for GUI display
    public String getAttendeeListForGUI() {
        StringBuilder attendeeDetails = new StringBuilder("Attendees for " + name + ":\n");
        for (Attendee attendee : attendees) {
            attendeeDetails.append("- ").append(attendee.getName())
                           .append(" (").append(attendee.getEmail()).append(")\n");
        }
        return attendeeDetails.toString();
    }

    //getters
    public String getSessionID() {
        return sessionID;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public Speaker getSpeaker() {
        return speaker;
    }
}