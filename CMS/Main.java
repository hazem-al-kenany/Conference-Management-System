import java.util.*;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        // Step 1: Create a conference
        // Create a single instance of the Conference
        Conference conference = new Conference("CONF001", "GAF-AI 2025", new Date(), new Date());

        // Start the Manager GUI and Attendee GUI with the shared Conference instance
        SwingUtilities.invokeLater(() -> new ManagerGUI(conference));
        SwingUtilities.invokeLater(() -> new AttendeeGUI(conference));
        
        
        
        // Step 2: Add sessions to the conference
        Session session1 = new Session("S001", "Introduction to LLMs", new Date(), "10:00 AM", "Room A", 50);
        Session session2 = new Session("S002", "Future of Quantum Computing", new Date(), "2:00 PM", "Room B", 30);
        conference.addSession(session1);
        conference.addSession(session2);
        
        // Step 2.5: Add speakers to the conference and assign them to sessions
        Speaker speaker1 = new Speaker("SP001", "Demo speaker 1", "demo1@gmail.com", "An expert in AI advancements.");
        Speaker speaker2 = new Speaker("SP002", "Demo speaker 2", "demo1@gmail.com", "Specialist in robotics and automation.");
        conference.addSpeaker(speaker1);
        conference.addSpeaker(speaker2);
        
        session1.assignSpeaker(speaker1);
        session2.assignSpeaker(speaker2);
        
        // Step 2.6: Display the full conference schedule and speakers
        System.out.println("\n--- Conference Schedule ---");
        conference.displaySchedule();

        System.out.println("\n--- Conference Speakers ---");
        conference.displaySpeakers();
                        
        // Step 3: Register attendees
        Attendee attendee1 = new Attendee("AT001", "Hazem 1", "elknanyhazem@gmail.com", "1234567890");
        Attendee attendee2 = new Attendee("AT002", "Hazem 2", "elknanyhazem@gmail.com", "0987654321");
        conference.registerAttendee(attendee1);
        conference.registerAttendee(attendee2);
        
        /*
        // Step 3: Simulate attendee login
        System.out.println("\n--- Attendee Login ---");
        Scanner scanner = new Scanner(System.in);

        // Simulate first attendee login
        System.out.print("Enter name for attendee 1: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter email for attendee 1: ");
        String email1 = scanner.nextLine();
        System.out.print("Enter phone number for attendee 1: ");
        String phone1 = scanner.nextLine();
        Attendee attendee1 = conference.logInAttendee(name1, email1, phone1);

        // Simulate second attendee login
        System.out.print("Enter name for attendee 2: ");
        String name2 = scanner.nextLine();
        System.out.print("Enter email for attendee 2: ");
        String email2 = scanner.nextLine();
        System.out.print("Enter phone number for attendee 2: ");
        String phone2 = scanner.nextLine();
        Attendee attendee2 = conference.logInAttendee(name2, email2, phone2);
        
        */
        
        // Step 3.5: Register attendees to specific sessions
        System.out.println("\n--- Registering for Sessions ---");
        session1.registerAttendee(attendee1);
        session2.registerAttendee(attendee2);
        
        // Simulating attendance
        System.out.println("\n--- Marking Attendance ---");
        conference.trackAttendanceWithFeedback("S001", "AT001"); // Hazem 1 attends Session 1
        conference.trackAttendanceWithFeedback("S002", "AT002"); // Hazem 2 attends Session 2

        
        // Step 4: Feedback submission
        System.out.println("\n--- Feedback Submission ---");
        attendee1.provideFeedback("S001", "Great session on LLMs!", 5, conference);
        attendee2.provideFeedback("S002", "Insightful discussion on Quantum Computing.", 4, conference);

        // Step 5: Notifications
        conference.enableNotifications(true);
        conference.notifyAllAttendees("Don't miss the keynote session at 9:00 AM tomorrow!");
        
        // Step 6: Certificate generation setup & Emailing
        EmailService emailService = new EmailService("TDGaming37@gmail.com", "ksgv krvd uxuv nasa"); // Replace with real credentials
        CertificateManager certificateManager = new CertificateManager(
                "Certificate of Participation\n" +
                "----------------------------------\n" +
                "This certifies that {name} has participated in the conference on {date}.\n",
                emailService // Pass the EmailService instance
        );
        
        // Step 6.1: Viewing and downloading certificates
        System.out.println("\n--- Downloading/Viewing Certificates ---");
        attendee1.viewCertificate(certificateManager); // View certificate for attendee 1
        attendee2.downloadCertificate(certificateManager); // Download certificate for attendee 2
        
        // Step 6.2: Simulate issuing certificates for all attendees
        System.out.println("\n--- Issuing Certificates ---");
        conference.issueCertificates(certificateManager); // Issue certificates for all attendees
        

        // Step 9: Report generation
        // Generate and print attendance report
        Report report = new Report("REPORT-1");
        
        String attendanceReport = report.generateAttendanceReportContent(conference.getSessions());
        System.out.println("\n--- Attendance Report ---");
        System.out.println(attendanceReport);
        
        // Generate and print feedback report
        System.out.println("\n--- Feedback Report ---");
        report.generateFeedbackReportContent(conference.getSessions());
        
        
        System.out.println("\n--- Searching Attendees ---");
        List<Attendee> foundAttendees = conference.searchAttendees("Hazem");
        for (Attendee attendee : foundAttendees) {
            System.out.println("Found Attendee: " + attendee.getName() + ", Email: " + attendee.getEmail());
        }

        System.out.println("\n--- Searching Sessions ---");
        List<Session> foundSessions = conference.searchSessions("Quantum");
        for (Session session : foundSessions) {
            System.out.println("Found Session: " + session.getName() + ", Room: " + session.getRoom());
        }
        /*
        System.out.println("\n--- Searching Feedback in Session 1 ---");
        List<Feedback> feedbackResults = session1.searchFeedback("great");
        for (Feedback feedback : feedbackResults) {
            System.out.println("Feedback: " + feedback.getFeedback());
        }
        */
        System.out.println("\n--- Searching Speakers ---");
        List<Speaker> foundSpeakers = conference.searchSpeakers("AI");
        for (Speaker speaker : foundSpeakers) {
            System.out.println("Found Speaker: " + speaker.getName() + " - " + speaker.getBio());
        }

        
        System.out.println("\n--- Saving Attendees and Sessions ---");
        conference.saveAttendees();
        conference.saveSessions();

        System.out.println("\n--- Saving Report ---");
        String reportContent = report.generateAttendanceReportContent(conference.getSessions());
        PersistenceManager.saveTextToFile(reportContent, "attendance_report.txt");
        
        
        System.out.println("\n--- Loading Attendees and Sessions ---");
        conference.loadAttendees();
        conference.loadSessions();
        System.out.println("Loaded Attendees:");
        for (Attendee attendee : conference.getAttendees()) {
            System.out.println(attendee.getName() + " - " + attendee.getEmail());
        }
        System.out.println("Loaded Sessions:");
        for (Session session : conference.getSessions()) {
            System.out.println(session.getSessionDetails());
        }

        System.out.println("\n--- Loading Report ---");
        String loadedReport = PersistenceManager.loadTextFromFile("attendance_report.txt");
        System.out.println("Loaded Report Content:\n" + loadedReport);
        
        // Save the notification log to a file
        System.out.println("\n--- Saving Notification Log ---");
        conference.saveNotificationsLog("notifications_log.txt");
        
        // Load the notification log from a file
        System.out.println("\n--- Loading Notification Log ---");
        conference.loadNotificationsLog("notifications_log.txt");
                
        // Test Email Sending
        System.out.println("\n--- Testing Email Sending ---");
        // Send attendance report via email
        emailService.sendEmail(
            "elknanyhazem@gmail.com", // Replace with recipient email
            "Attendance Report",
            reportContent
        );
        
        // Email notifications log if needed
        String notificationsLog = PersistenceManager.loadTextFromFile("notifications_log.txt");
        emailService.sendEmail(
            "elknanyhazem@gmail.com", // Replace with recipient email
            "Notifications Log",
            notificationsLog
        );
        
        // Send a test email with an attachment
        emailService.sendEmailWithAttachment(
            "elknanyhazem@gmail.com", // Replace with recipient email
            "Test Email with Attachment",
            "This is a test email with a certificate attached.",
            "certificates/certificate_AT001.txt" // Test attachment path
        );
        
        // Issue certificates and email them
        System.out.println("\n--- Issuing Certificates and Emailing ---");
        conference.issueCertificates(certificateManager);


    }
}