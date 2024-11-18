import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class ConferenceGUI {
    private Conference conference;

    public ConferenceGUI() {
        this.conference = new Conference("CONF001", "GAF-AI 2025", new Date(), new Date());
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Conference Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        //add a session button
        JButton addSessionButton = new JButton("Add Session");
        addSessionButton.addActionListener(e -> {
            String sessionID = JOptionPane.showInputDialog("Enter Session ID:");
            String sessionName = JOptionPane.showInputDialog("Enter Session Name:");
            String room = JOptionPane.showInputDialog("Enter Room:");
            Session session = new Session(sessionID, sessionName, new Date(), "10:00 AM", room, 50);
            conference.addSession(session);
            JOptionPane.showMessageDialog(frame, "Session added successfully!");
        });

        //register an attendee button
        JButton registerAttendeeButton = new JButton("Register Attendee");
        registerAttendeeButton.addActionListener(e -> {
            String attendeeID = JOptionPane.showInputDialog("Enter Attendee ID:");
            String name = JOptionPane.showInputDialog("Enter Name:");
            String email = JOptionPane.showInputDialog("Enter Email:");
            String phone = JOptionPane.showInputDialog("Enter Phone Number:");
            Attendee attendee = new Attendee(attendeeID, name, email, phone);
            conference.registerAttendee(attendee);
            JOptionPane.showMessageDialog(frame, "Attendee registered successfully!");
        });

        //display attendees and sessions button
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

        //add buttons to the panel
        panel.add(addSessionButton);
        panel.add(registerAttendeeButton);
        panel.add(displayDataButton);

        //add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConferenceGUI::new);
    }
}