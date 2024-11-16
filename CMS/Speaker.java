public class Speaker {
    private String speakerID;
    private String name;
    private String bio;

    public Speaker(String speakerID, String name, String bio) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
    }

    //get the speaker's biography
    public String getBio() {
        return "Speaker Name: " + name + "\nBiography: " + bio;
    }

    //getters & setters
    public String getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return bio;
    }

    public void setBiography(String bio) {
        this.bio = bio;
    }
}
