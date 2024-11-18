public class Speaker extends Person{
    private String speakerID;
    private String name;
    private String bio;
    private String email;

    public Speaker(String speakerID, String name, String email, String bio) {
        super(name, email); //call parent class constructor (Person)
        this.speakerID = speakerID;
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
