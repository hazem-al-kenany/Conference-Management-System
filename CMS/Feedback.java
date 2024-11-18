public class Feedback {
    private String feedbackID;
    private String attendeeID;
    private int rating;
    private String comment;

    public Feedback(String feedbackID, String attendeeID, int rating, String comment) {
        this.feedbackID = feedbackID;
        this.attendeeID = attendeeID;
        this.rating = validateRating(rating);
        this.comment = comment;
    }

    //validate and set the rating
    private int validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Rating must be between 1 and 5. Defaulting to 3.");
            return 3; //default to a neutral rating if invalid
        }
        return rating;
    }

    //submit feedback
    public static Feedback submitFeedback(String attendeeID, int rating, String comment) {
        String feedbackID = "FB-" + System.currentTimeMillis(); // Generate a unique ID
        Feedback feedback = new Feedback(feedbackID, attendeeID, rating, comment);
        System.out.println("Feedback submitted successfully: " + feedback.getFeedback());
        return feedback;
    }

    //get formatted feedback details
    public String getFeedback() {
        return "Feedback ID: " + feedbackID + ", Attendee ID: " + attendeeID +
                ", Rating: " + rating + ", Comment: " + comment;
    }

    //getters & setters
    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getAttendeeID() {
        return attendeeID;
    }

    public void setAttendeeID(String attendeeID) {
        this.attendeeID = attendeeID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = validateRating(rating);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}