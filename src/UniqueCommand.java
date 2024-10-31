import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UniqueCommand {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: unique <file1> <file2> ... <fileN>");
            return;
        }

        Map<String, Integer> lineCounts = new HashMap<>();

        // Read each file and count occurrences of each line
        for (String filename : args) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lineCounts.put(line, lineCounts.getOrDefault(line, 0) + 1);
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + filename + ": " + e.getMessage());
            }
        }

        // Print unique lines with their counts
        for (Map.Entry<String, Integer> entry : lineCounts.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }
}