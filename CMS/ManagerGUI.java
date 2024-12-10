import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ManagerGUI {
    private Conference conference; //shared conference instance
    private int sessionCounter; //tracks session ID generation

    //constructor initializes the GUI and session counter
    public ManagerGUI(Conference conference) {
        this.conference = conference;
        this.sessionCounter = initializeSessionCounter(); //start sessionCounter based on existing sessions
        createAndShowGUI();
    }

    //initialize session counter by finding the highest existing session ID
    private int initializeSessionCounter() {
        int maxSessionNumber = 0;
        for (Session session : conference.getSessions()) {
            try {
                int sessionNumber = Integer.parseInt(session.getSessionID().replaceAll("\\D", "")); //extract numeric part
                maxSessionNumber = Math.max(maxSessionNumber, sessionNumber); //update max
            } catch (NumberFormatException ignored) {
                //skip non-standard session IDs
            }
        }
        return maxSessionNumber + 1; //start after highest existing session number
    }

    //sets up and displays the GUI
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Manager - Conference Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); //layout for buttons

        //button to add a new session
        JButton addSessionButton = new JButton("Add Session");
        addSessionButton.addActionListener(e -> {
            String sessionID = String.format("S%03d", sessionCounter++); //auto-generate session ID
            String sessionName = JOptionPane.showInputDialog("Enter Session Name:");
            String room = JOptionPane.showInputDialog("Enter Room:");

            //validate session name and room
            if (!isValidName(sessionName)) {
                JOptionPane.showMessageDialog(frame, "Invalid session name. Please use only letters and spaces.");
                return;
            }

            if (room == null || room.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Invalid room name. Room cannot be empty.");
                return;
            }

            String time = JOptionPane.showInputDialog("Enter Session Time (e.g., 10:00 AM):");
            if (time == null || time.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Invalid time. Time cannot be empty.");
                return;
            }

            Session session = new Session(sessionID, sessionName, new Date(), time, room, 50); //create session
            conference.addSession(session); //add session to conference
            JOptionPane.showMessageDialog(frame, "Session added successfully! ID: " + sessionID);
        });

        //button to register a new attendee
        JButton registerAttendeeButton = new JButton("Register Attendee");
        registerAttendeeButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Name:");
            String email = JOptionPane.showInputDialog("Enter Email:");
            String phone = JOptionPane.showInputDialog("Enter Phone Number:");

            //validate attendee inputs
            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid name. Please use only letters and spaces.");
                return;
            }
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email format. Please use a valid email.");
                return;
            }

            String attendeeID = "AT" + (conference.getAttendees().size() + 1); //generate attendee ID
            Attendee attendee = new Attendee(attendeeID, name, email, phone); //create attendee
            conference.registerAttendee(attendee); //register attendee
            JOptionPane.showMessageDialog(frame, "Attendee registered successfully! ID: " + attendeeID);
        });

        //button to display attendees and sessions
        JButton displayDataButton = new JButton("Display Data");
        displayDataButton.addActionListener(e -> {
            StringBuilder attendeesList = new StringBuilder("Attendees:\n");
            for (Attendee attendee : conference.getAttendees()) {
                attendeesList.append(attendee.getName()).append(" - ").append(attendee.getEmail()).append("\n");
            }

            StringBuilder sessionsList = new StringBuilder("Sessions:\n");
            for (Session session : conference.getSessions()) {
                sessionsList.append(session.getName()).append(" in Room ").append(session.getRoom()).append("\n");
            }

            JTextArea textArea = new JTextArea(attendeesList.toString() + "\n" + sessionsList.toString());
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Conference Data", JOptionPane.INFORMATION_MESSAGE);
        });

        //button to generate a report
        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(e -> {
            if (conference.getSessions().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No sessions available. Cannot generate report.");
                return;
            }
            conference.generateReport(); //generate conference report
            JOptionPane.showMessageDialog(frame, "Report generated and saved to 'conference_report.txt'");
        });

        //add buttons to the panel
        panel.add(addSessionButton);
        panel.add(registerAttendeeButton);
        panel.add(displayDataButton);
        panel.add(generateReportButton);

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

    //entry point for the manager GUI
    public static void main(String[] args) {
        Conference conference = new Conference("CONF001", "GAF-AI 2025", new Date(), new Date());

        //load attendees and sessions from files
        conference.loadAttendees();
        conference.loadSessions();

        //launch manager and attendee GUIs
        SwingUtilities.invokeLater(() -> new ManagerGUI(conference));
        SwingUtilities.invokeLater(() -> new AttendeeGUI(conference)); //launch attendee GUI
    }
}