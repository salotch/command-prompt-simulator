import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    private boolean showHidden = false;
    private String directoryPath = ".";

    // Methods to configure options
    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void execute(String[] args) {
        // Parse arguments to set options
        parseArguments(args);

        // Create a file object for the specified directory
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Error: " + directoryPath + " is not a directory.");
            return;
        }

        // List files based on the `showHidden` flag
        StringBuilder output = new StringBuilder();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (showHidden || !file.isHidden()) {
                    output.append(file.getName()).append("\n");
                }
            }
        } else {
            System.out.println("Error: Unable to access directory contents.");
            return;
        }

        // Print the result to the console
        System.out.print(output.toString());

        // Returning to current directory
        directoryPath = ".";
    }

    // Helper method to parse command arguments
    private void parseArguments(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
            if (arg.equals("-a")) {
                showHidden = true; // Enable hidden files display
            } else if (arg.equals("-r")){
                // code for ls -r
                continue;
            }else if (!arg.equals(">") && !arg.equals(">>") && !arg.equals("|") && !arg.equals("ls")) {
                // If it's not a redirection or pipe symbol, assume it's a directory path
                directoryPath = arg;
            }
        }

    }

}
