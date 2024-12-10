import java.util.Date;
import java.util.List;

public class Report {
    private String reportID; //unique id for the report
    private Date dateGenerated; //date when the report was generated

    //constructor to initialize report with id and current date
    public Report(String reportID) {
        this.reportID = reportID;
        this.dateGenerated = new Date(); //auto-set current date
    }

    //generate feedback summary for all sessions
    public void generateFeedbackReportContent(List<Session> sessionList) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Feedback Report\n");
        reportContent.append("Report ID: ").append(reportID).append("\n");
        reportContent.append("Date Generated: ").append(dateGenerated).append("\n");
        reportContent.append("--------------------------------------------------\n");
    
        for (Session session : sessionList) {
            reportContent.append("Session ID: ").append(session.getSessionID()).append("\n");
            reportContent.append("Session Name: ").append(session.getName()).append("\n");
    
            if (session.getFeedbackList().isEmpty()) { //check if feedback exists
                reportContent.append("No feedback available for this session.\n");
            } else {
                reportContent.append("Feedback:\n");
                for (Feedback feedback : session.getFeedbackList()) { //iterate through feedback
                    reportContent.append("- Attendee ID: ").append(feedback.getAttendeeID()).append("\n");
                    reportContent.append("  Rating: ").append(feedback.getRating()).append("\n");
                    reportContent.append("  Comment: ").append(feedback.getComment()).append("\n");
                }
            }
            reportContent.append("--------------------------------------------------\n");
        }
    
        System.out.println(reportContent.toString()); //print report to console
    }

    //generate attendance summary for all sessions
    public String generateAttendanceReportContent(List<Session> sessionList) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Attendance Report\n");
        reportContent.append("Report ID: ").append(reportID).append("\n");
        reportContent.append("Date Generated: ").append(dateGenerated).append("\n");
        reportContent.append("--------------------------------------------------\n");

        for (Session session : sessionList) {
            reportContent.append("Session ID: ").append(session.getSessionID()).append("\n");
            reportContent.append("Session Name: ").append(session.getName()).append("\n");
            reportContent.append("Registered Attendees: ").append(session.getAttendees().size()).append("\n");
            reportContent.append("Max Attendees: ").append(session.getMaxAttendees()).append("\n");
            reportContent.append("--------------------------------------------------\n");
        }

        return reportContent.toString(); //return the attendance report as a string
    }

    //getter for report id
    public String getReportID() {
        return reportID;
    }

    //getter for the date report was generated
    public Date getDateGenerated() {
        return dateGenerated;
    }
}