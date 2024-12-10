import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notifications {
    private List<String> notificationLog; //log of sent notifications

    //constructor to initialize notification log
    public Notifications() {
        this.notificationLog = new ArrayList<>();
    }

    //send a notification to a specific attendee
    public void sendToAttendee(Attendee attendee, String message) {
        Date timestamp = new Date(); //current timestamp
        String notification = "To: " + attendee.getName() + " (" + attendee.getEmail() + ")\n"
                + "Message: " + message + "\n"
                + "Sent on: " + timestamp + "\n";
        System.out.println(notification); //print notification

        notificationLog.add(notification); //add notification to the log
    }

    //send a notification to all attendees
    public void sendToAll(List<Attendee> attendees, String message) {
        Date timestamp = new Date(); //current timestamp
        for (Attendee attendee : attendees) {
            String notification = "To: " + attendee.getName() + " (" + attendee.getEmail() + ")\n"
                    + "Message: " + message + "\n"
                    + "Sent on: " + timestamp + "\n";
            System.out.println(notification); //print notification

            notificationLog.add(notification); //add notification to the log
        }
    }

    //get all notifications from the log
    public List<String> getNotificationLog() {
        return notificationLog;
    }

    //save notification log to a file
    public void saveLogToFile(String fileName) {
        try {
            PersistenceManager.saveTextToFile(String.join("\n", notificationLog), fileName); //use PersistenceManager
            System.out.println("Notification log saved to: " + fileName);
        } catch (Exception e) {
            System.err.println("Error saving notification log: " + e.getMessage());
        }
    }

    //load notification log from a file
    public void loadLogFromFile(String fileName) {
        try {
            String logContent = PersistenceManager.loadTextFromFile(fileName); //read content from file
            if (logContent.isEmpty()) {
                System.out.println("No notifications found in the file.");
                return;
            }
            String[] notifications = logContent.split("\n"); //split content into individual notifications
            for (String notification : notifications) {
                notificationLog.add(notification);
            }
            System.out.println("Notification log loaded from: " + fileName);
        } catch (Exception e) {
            System.err.println("Error loading notification log: " + e.getMessage());
        }
    }
}