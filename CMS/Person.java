import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name; //person's name
    private String email; //person's email

    //constructor to initialize name and email
    public Person(String name, String email) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty"); //validate name
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email cannot be null or empty"); //validate email

        this.name = name;
        this.email = email;
    }

    //getter for name
    public String getName() {
        return name;
    }

    //setter for name
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty"); //validate name
        this.name = name;
    }

    //getter for email
    public String getEmail() {
        return email;
    }

    //setter for email
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email cannot be null or empty"); //validate email
        this.email = email;
    }
}