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

        // Remove the first argument ("cat") and pass the rest as file names
        String[] fileNames = new String[args.length - 1];
        System.arraycopy(args, 1, fileNames, 0, fileNames.length);
        cat(fileNames);
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
