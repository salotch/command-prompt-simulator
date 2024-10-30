import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CatCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: cat <file_name1> <file_name2> ...");
            return;
        }

        // Initialize a list for file names
        String[] fileNames = new String[args.length - 1];
        int fileCount = 0;

        // Process arguments and separate file names from redirection symbols
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals(">") || args[i].equals(">>") || args[i].equals("|")) {
                // Stop processing once we hit a redirection or pipe symbol
                break;
            } else {
                fileNames[fileCount++] = args[i];
            }
        }

        // Create a new array with the actual file names
        String[] finalFileNames = new String[fileCount];
        System.arraycopy(fileNames, 0, finalFileNames, 0, fileCount);

        cat(finalFileNames);

    }

    private void cat(String[] fileNames) {
        for (String fileName : fileNames) {
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("Error: File '" + fileName + "' does not exist.");
                continue;  // Skip to the next file if this one doesn't exist
            }

            System.out.println("Contents of " + fileName + ":");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file " + fileName + ": " + e.getMessage());
            }
            System.out.println();  // Separate contents of different files with a newline
        }
    }

}
