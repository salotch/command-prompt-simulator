import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class MoreCommand implements Command {
    @Override
    public void execute(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: more <file1> <file2> ... <fileN>");
            return;
        }
        int linesPerPage = 10; // Number of lines to display per page

        Map<String, Integer> lineCounts = new HashMap<>();

        // Read each file and count occurrences of each line
        for (String filename : args) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                int lineCount = 0;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    lineCount++;

                    if (lineCount == linesPerPage) {
                        System.out.print("Press Enter to continue...");
                        new Scanner(System.in).nextLine(); // Wait for user input
                        lineCount = 0; // Reset line count for the next page
                    }
                }

            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }

        // Print unique lines with their counts
        for (Map.Entry<String, Integer> entry : lineCounts.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }

    }

}