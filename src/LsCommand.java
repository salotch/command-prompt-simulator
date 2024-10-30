import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    private boolean showHidden = false;
    private boolean recursive = false;
    private String directoryPath = ".";
    private StringBuilder output = new StringBuilder();  // Store output here for testing

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    // Getter to retrieve the output for testing
    public String getOutput() {
        return output.toString();
    }

    @Override
    public void execute(String[] args) {
        // Clear previous output for fresh execution
        output.setLength(0);

        // Parse arguments
        parseArguments(args);

        // Start listing files
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            output.append("Error: ").append(directoryPath).append(" is not a directory.\n");
            return;
        }

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
            } else if (arg.equals("-ra") || arg.equals("-ar")) {    // For both recursive and hidden
                showHidden = true;
                recursive = true;
            }  else if (!arg.equals(">") && !arg.equals(">>") && !arg.equals("|") && !arg.equals("ls")) {
                directoryPath = arg;  // Assume it's a directory path if not a flag or redirection
            }/* else if (arg.equals(">") || arg.equals(">>") || arg.equals("|")) {
                break;
            }*/
        }
    }

    private void listFiles(File directory, int depth, StringBuilder output) {
        // Indentation to reflect directory depth for recursive display
        String indent = "  ".repeat(depth);

        // List files and directories in the current directory
        File[] files = directory.listFiles();
        if (files == null) {
            output.append("Unable to access directory: ").append(directory.getPath()).append("\n");
            return;
        }

        for (File file : files) {
            if (showHidden || !file.isHidden()) {
                output.append(indent).append(file.getName()).append("\n");

                // Recurse if the file is a directory and -r is enabled
                if (file.isDirectory() && recursive) {
                    listFiles(file, depth + 1, output);
                }
            }
        }
    }
}
