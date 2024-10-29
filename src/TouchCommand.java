import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command {

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No filename provided for touch command.");
            return;
        }

        for (String fileName : args) {
            File file = new File(fileName);
            if (fileName.equals("touch"))
                continue;

            try {
                if (file.exists()) {
                    // If the file exists, update its last modified timestamp
                    boolean updated = file.setLastModified(System.currentTimeMillis());
                    if (!updated) {
                        System.out.println("Warning: Could not update timestamp for " + fileName);
                    }
                } else {
                    // If the file doesn't exist, create it
                    boolean created = file.createNewFile();
                    if (created) {
                        System.out.println("File created: " + fileName);
                    } else {
                        System.out.println("Error: Could not create file " + fileName);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: Unable to create or modify file " + fileName + ". " + e.getMessage());
            }
        }
    }
}
