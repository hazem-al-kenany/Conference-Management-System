import java.util.Date;
import java.util.List;

public class Report {
    private String reportID; 
    private Date dateGenerated;

    // Constructor
    public Report(String reportID) {
        this.reportID = reportID;
        this.dateGenerated = new Date(); //auto set the current date
    }

    //generate feedback summary report
    public void generateFeedbackReport(List<Feedback> feedbackList) {
        System.out.println("Feedback Report");
        System.out.println("Report ID: " + reportID);
        System.out.println("Date Generated: " + dateGenerated);
        System.out.println("--------------------------------------------------");
        for (Feedback feedback : feedbackList) {
            System.out.println("Attendee ID: " + feedback.getAttendeeID());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Comment: " + feedback.getComment());
            System.out.println("--------------------------------------------------");
        }
    }

    //generate attendance summary report
    public void generateAttendanceReport(List<Session> sessionList) {
        System.out.println("Attendance Report");
        System.out.println("Report ID: " + reportID);
        System.out.println("Date Generated: " + dateGenerated);
        System.out.println("--------------------------------------------------");
        for (Session session : sessionList) {
            System.out.println("Session ID: " + session.getSessionID());
            System.out.println("Session Name: " + session.getName());
            System.out.println("Registered Attendees: " + session.getAttendees().size());
            System.out.println("Max Attendees: " + session.getMaxAttendees());
            System.out.println("--------------------------------------------------");
        }
    }

    //getters & setters
    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(Date dateGenerated) {
        this.dateGenerated = dateGenerated;
    }
}
