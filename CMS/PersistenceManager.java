import java.io.*;
import java.util.List;

public class PersistenceManager {
    private static final String DEFAULT_DIRECTORY = "data/"; //default directory for saved files

    //save any list to a file
    public static <T> void saveToFile(List<T> list, String fileName) {
        try {
            //ensure the directory exists
            File dir = new File(DEFAULT_DIRECTORY);
            if (!dir.exists()) {
                dir.mkdirs(); //create directory if it doesn't exist
            }
            
            //save the file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEFAULT_DIRECTORY + fileName))) {
                oos.writeObject(list);
                System.out.println("Data saved to file: " + DEFAULT_DIRECTORY + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load any list from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        try {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DEFAULT_DIRECTORY + fileName))) {
                return (List<T>) ois.readObject();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + DEFAULT_DIRECTORY + fileName);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class mismatch during deserialization: " + e.getMessage());
        }
        return null; //return null if loading fails
    }
}