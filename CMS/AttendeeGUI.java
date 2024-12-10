import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AttendeeGUI {
    private Conference conference; //shared conference instance
    private Attendee loggedInAttendee; //tracks currently logged-in attendee

    //constructor initializes the GUI with a shared conference instance
    public AttendeeGUI(Conference conference) {
        this.conference = conference;
        createAndShowGUI();
    }

    //sets up and displays the GUI
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Attendee - Conference Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); //grid layout for buttons

        //button for login or registration
        JButton loginRegisterButton = new JButton("Log In / Register");
        loginRegisterButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Name:");
            String email = JOptionPane.showInputDialog("Enter Email:");
            String phone = JOptionPane.showInputDialog("Enter Phone Number:");

            //validate user input
            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid name. Please use only letters and spaces.");
                return;
            }
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email format. Please use a valid email.");
                return;
            }

            //log in or register the attendee
            Attendee attendee = conference.logInAttendee(name, email, phone);
            loggedInAttendee = attendee; //update the logged-in attendee
            JOptionPane.showMessageDialog(frame, 
                "Welcome, " + attendee.getName() + "! Your Attendee ID: " + attendee.getAttendeeID());
        });

        //button for browsing sessions
        JButton browseSessionsButton = new JButton("Browse Sessions");
        browseSessionsButton.addActionListener(e -> {
            String sessionDetails = conference.getFormattedSessionsForGUI();
            if (sessionDetails.equals("Sessions:\n")) {
                JOptionPane.showMessageDialog(frame, "No sessions available.");
            } else {
                JTextArea textArea = new JTextArea(sessionDetails);
                textArea.setEditable(false);
                JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Sessions", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //button for registering a session
        JButton registerSessionButton = new JButton("Register for Session");
        registerSessionButton.addActionListener(e -> {
            if (loggedInAttendee == null) {
                JOptionPane.showMessageDialog(frame, "Please log in first!");
                return;
            }

            String sessionID = JOptionPane.showInputDialog("Enter Session ID to Register:");
            Session session = conference.findSessionByID(sessionID);

            if (session != null) {
                loggedInAttendee.createSchedule(session); //add session to schedule
                JOptionPane.showMessageDialog(frame, "Successfully registered for session: " + session.getName());

                //send registration email
                EmailService emailService = new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa");
                emailService.sendEmail(
                    loggedInAttendee.getEmail(),
                    "Session Registration Confirmation",
                    "You have successfully registered for the session: " + session.getName()
                );
            } else {
                JOptionPane.showMessageDialog(frame, "Session with ID " + sessionID + " not found.");
            }
        });

        //button for attending a session
        JButton attendSessionButton = new JButton("Attend Session");
        attendSessionButton.addActionListener(e -> {
            if (loggedInAttendee == null) {
                JOptionPane.showMessageDialog(frame, "Please log in first!");
                return;
            }

            String sessionID = JOptionPane.showInputDialog("Enter Session ID:");
            Session session = conference.findSessionByID(sessionID);

            if (session == null) {
                JOptionPane.showMessageDialog(frame, "Session with ID " + sessionID + " does not exist.");
                return;
            }

            if (!loggedInAttendee.getSchedule().contains(session)) {
                JOptionPane.showMessageDialog(frame, "You cannot attend a session you are not registered for.");
                return;
            }

            loggedInAttendee.attendSession(session); //mark attendance
            session.markAttendance(loggedInAttendee); //update attendance in session
            JOptionPane.showMessageDialog(frame, "Attendance marked for session: " + session.getName());
        });

        //button for providing feedback
        JButton provideFeedbackButton = new JButton("Provide Feedback");
        provideFeedbackButton.addActionListener(e -> {
            if (loggedInAttendee == null) {
                JOptionPane.showMessageDialog(frame, "Please log in first!");
                return;
            }

            String sessionID = JOptionPane.showInputDialog("Enter Session ID:");
            if (sessionID == null || sessionID.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Session ID cannot be empty.");
                return;
            }

            sessionID = sessionID.toUpperCase(); //normalize to uppercase
            Session session = conference.findSessionByID(sessionID);

            if (session == null) {
                JOptionPane.showMessageDialog(frame, "Session with ID " + sessionID + " does not exist.");
                return;
            }

            if (!loggedInAttendee.getAttendedSessions().contains(sessionID)) {
                JOptionPane.showMessageDialog(frame, "You cannot give feedback for a session you have not attended.");
                return;
            }

            String feedback = JOptionPane.showInputDialog("Enter Your Feedback:");
            if (feedback == null || feedback.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Feedback cannot be empty.");
                return;
            }

            String ratingString = JOptionPane.showInputDialog("Rate the Session (1-5):");
            try {
                int rating = Integer.parseInt(ratingString);
                if (rating < 1 || rating > 5) throw new NumberFormatException();
                conference.submitFeedback(loggedInAttendee.getAttendeeID(), sessionID, rating, feedback);
                JOptionPane.showMessageDialog(frame, "Feedback submitted successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid rating. Please enter a number between 1 and 5.");
            }
        });

        //button for viewing certificates
        JButton viewCertificateButton = new JButton("View Certificate");
        viewCertificateButton.addActionListener(e -> {
            if (loggedInAttendee == null) {
                JOptionPane.showMessageDialog(frame, "Please log in first!");
                return;
            }

            if (loggedInAttendee.getAttendedSessions().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No sessions attended. Certificate cannot be issued.");
                return;
            }

            CertificateManager certificateManager = new CertificateManager(
                "Certificate of Participation\n" +
                "----------------------------------\n" +
                "This certifies that {name} has participated in the conference on {date}.\n",
                new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa")
            );
            loggedInAttendee.viewCertificate(certificateManager);
            JOptionPane.showMessageDialog(frame, "Certificate viewed successfully.");
        });

        //add all buttons to the panel
        panel.add(loginRegisterButton);
        panel.add(browseSessionsButton);
        panel.add(registerSessionButton);
        panel.add(attendSessionButton);
        panel.add(provideFeedbackButton);
        panel.add(viewCertificateButton);

        frame.add(panel); //add panel to frame
        frame.setVisible(true); //display frame
    }

    //validate name (only letters and spaces allowed)
    private boolean isValidName(String name) {
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    //validate email format
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    //entry point for the attendee GUI
    public static void main(String[] args) {
        Conference conference = new Conference("CONF001", "GAF-AI 2025", new Date(), new Date());
        
        //load attendees and sessions from files
        conference.loadAttendees();
        conference.loadSessions();
    
        //launch the attendee GUI
        SwingUtilities.invokeLater(() -> new AttendeeGUI(conference));
        
        //save data on application shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(conference::saveData));
    }
}