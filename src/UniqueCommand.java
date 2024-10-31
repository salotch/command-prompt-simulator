import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UniqueCommand implements Command {

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: unique <file1> <file2> ... <fileN>");
            return;
        }

        Map<String, Integer> lineCounts = new HashMap<>();

        // Read each file and count occurrences of each line
        for (int i = 1; i < args.length; i++) {
             File file = new File(args[i]);
             if (file.isDirectory()) {
                System.out.println("Error: '" + args[i] + "' is not a file.");
                continue; // Skip to the next file if this one doesn't exist
            }
            else
            {
                lineCounts.put(args[i], lineCounts.getOrDefault(args[i], 0) + 1);
            }
         
        }

        // Print unique lines with their counts
        for (Map.Entry<String, Integer> entry : lineCounts.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}