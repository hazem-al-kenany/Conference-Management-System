import java.io.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PersistenceManager {
    private static String directory = "data/"; // Default directory for saving files

    // Set a new directory
    public static void setDirectory(String newDirectory) {
        directory = newDirectory.endsWith("/") ? newDirectory : newDirectory + "/";
    }

    // Save a list of objects to a file (serialization)
    public static <T> void saveToFile(List<T> list, String fileName) {
        if (list == null || list.isEmpty()) {
            System.err.println("Cannot save null or empty list.");
            return;
        }
        try {
            ensureDirectoryExists();
            String fullPath = directory + fileName;
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
                oos.writeObject(list);
                System.out.println("Data saved to file: " + fullPath);
            }
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load a list of objects from a file (deserialization)
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        String fullPath = directory + fileName;
        try {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
                return (List<T>) ois.readObject();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fullPath);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class mismatch during deserialization: " + e.getMessage());
        }
        return new ArrayList<>(); // Return a modifiable list if loading fails
    }


    // Save plain text to a file (for reports)
    public static void saveTextToFile(String content, String fileName) {
        if (content == null || content.trim().isEmpty()) {
            System.err.println("Cannot save empty or null content.");
            return;
        }
        try {
            ensureDirectoryExists();
            String fullPath = directory + fileName;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
                writer.write(content);
                System.out.println("Text saved to file: " + fullPath);
            }
        } catch (IOException e) {
            System.err.println("Error saving text to file: " + e.getMessage());
        }
    }

    // Load plain text from a file (for reports)
    public static String loadTextFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        String fullPath = directory + fileName;
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fullPath);
        } catch (IOException e) {
            System.err.println("Error reading text from file: " + e.getMessage());
        }
        return content.toString();
    }

    // Search within a list
    public static <T> List<T> search(List<T> list, Predicate<T> predicate) {
        if (list == null || list.isEmpty()) {
            System.err.println("Cannot search an empty or null list.");
            return List.of();
        }
        return list.stream()
                   .filter(predicate)
                   .collect(Collectors.toList());
    }

    // Ensure the directory exists
    private static void ensureDirectoryExists() {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}