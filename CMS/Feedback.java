import java.io.Serializable;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private String feedbackID; //unique id for the feedback
    private String attendeeID; //id of the attendee giving the feedback
    private String sessionID; //id of the session being reviewedsearchFeedback
    private int rating; //rating given for the session
    private String comment; //feedback comment

    //constructor to initialize feedback attributes
    public Feedback(String feedbackID, String attendeeID, String sessionID, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.sessionID = sessionID;
        this.rating = validateRating(rating); //validate the rating input
        this.comment = comment;
    }

    //validate and set the rating
    private int validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5. Defaulting to 3.");
            return 3; //default rating if invalid
        }
        return rating;
    }

    //get formatted feedback details for display
    public String getFeedback() {
        return String.format(
            "Feedback ID: %s\nAttendee ID: %s\nSession ID: %s\nRating: %d\nComment: %s",
            feedbackID, attendeeID, sessionID, rating, comment
        );
    }

    //getter for feedback id
    public String getFeedbackID() {
        return feedbackID;
    }

    //setter for feedback id
    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    //getter for attendee id
    public String getAttendeeID() {
        return attendeeID;
    }

    //setter for attendee id
    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
    }

    //getter for session id
    public String getSessionID() {
        return sessionID;
    }

    //setter for session id
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    //getter for rating
    public int getRating() {
        return rating;
    }

    //setter for rating with validation
    public void setRating(int rating) {
        this.rating = validateRating(rating);
    }

    //getter for comment
    public String getComment() {
        return comment;
    }

    //setter for comment
    public void setComment(String comment) {
        this.comment = comment;
    }

    //determine feedback type based on rating
    public String getFeedbackType() {
        if (rating >= 4) return "Positive"; //positive feedback for high rating
        if (rating == 3) return "Neutral"; //neutral feedback for mid rating
        return "Negative"; //negative feedback for low rating
    }
}