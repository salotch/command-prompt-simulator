import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    private boolean showHidden = false;
    private boolean recursive = false;
    private String directoryPath = ".";

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void execute(String[] args) {
        // Parse arguments
        parseArguments(args);

        // Start listing files
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Error: " + directoryPath + " is not a directory.");
            return;
        }

        StringBuilder output = new StringBuilder();

        // List files with recursive option if required
        listFiles(directory, 0, output);

        // Print the result to the console
        System.out.print(output.toString());

        // Reset flags for next command execution
        showHidden = false;
        recursive = false;
        directoryPath = ".";
    }

    private void parseArguments(String[] args) {
        for (String arg : args) {
            if (arg.equals("-a")) {
                showHidden = true;
            } else if (arg.equals("-r")) {
                recursive = true;
            } else if (!arg.equals(">") && !arg.equals(">>") && !arg.equals("|") && !arg.equals("ls")) {
                directoryPath = arg;  // Assume it's a directory path if not a flag or redirection
            } else if (arg.equals(">") || arg.equals(">>") || arg.equals("|")) {
                break;
            }
        }
    }

    private void listFiles(File directory, int depth, StringBuilder output) {
        // Indentation to reflect directory depth for recursive display
        String indent = "  ".repeat(depth);

        // List files and directories in the current directory
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Unable to access directory: " + directory.getPath());
            return;
        }

        for (File file : files) {
            if (showHidden || !file.isHidden()) {
                //System.out.println(indent + file.getName());
                output.append(indent).append(file.getName()).append("\n");

                // Recurse if the file is a directory and -r is enabled
                if (file.isDirectory() && recursive) {
                    listFiles(file, depth + 1, output);
                }
            }
        }
    }
}


/*    public void execute(String[] args) {
        // Parse arguments to set options
        parseArguments(args);

        // Create a file object for the specified directory
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Error: " + directoryPath + " is not a directory.");
            return;
        }

        // List files based on the `showHidden` flag
        StringBuilder output = new StringBuilder(); // ----------------------------
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

        // Put array sort line here Alaa

        // Print the result to the console
        System.out.print(output.toString());

        // Returning to current directory
        directoryPath = ".";
    }*/