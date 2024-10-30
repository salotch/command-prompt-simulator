import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    private final ArrayList<String> currentPathList;
    private boolean showHidden = false;
    private boolean recursive = false;
    private String directoryPath;
    private final StringBuilder output = new StringBuilder();  // Store output for testing

    public LsCommand(ArrayList<String> currentPathList) {
        this.currentPathList = currentPathList;
        this.directoryPath = String.join(File.separator, currentPathList); // Set initial directory path
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    // Getter to retrieve the output for testing
    public String getOutput() {
        return output.toString();
    }

    @Override
    public void execute(String[] args) {
        output.setLength(0);  // Clear previous output for fresh execution
        parseArguments(args);  // Parse any flags or directory path arguments

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            output.append("Error: ").append(directoryPath).append(" is not a directory.\n");
            System.out.print(output);
            return;
        }

        // List files with recursive option if required
        listFiles(directory, 0, output);
        System.out.print(output);

        // Reset flags and directory path for the next command execution
        showHidden = false;
        recursive = false;
        directoryPath = String.join(File.separator, currentPathList);  // Reset to default path
    }

    private void parseArguments(String[] args) {
        for (String arg : args) {
            if (arg.equals("-a")) {
                showHidden = true;
            } else if (arg.equals("-r")) {
                recursive = true;
            } else if (arg.equals("-ra") || arg.equals("-ar")) {  // For both recursive and hidden
                showHidden = true;
                recursive = true;
            } else if (!arg.equals(">") && !arg.equals(">>") && !arg.equals("|") && !arg.equals("ls")) {
                // Assume it’s a directory path if it's not a flag or redirection
                directoryPath = arg;
            }
        }
    }

    private void listFiles(File directory, int depth, StringBuilder output) {
        String indent = "  ".repeat(depth);  // Indentation for recursive display
        File[] files = directory.listFiles();

        if (files == null) {
            output.append("Unable to access directory: ").append(directory.getPath()).append("\n");
            return;
        }

        for (File file : files) {
            if (showHidden || !file.isHidden()) {
                output.append(indent).append(file.getName()).append("\n");

                // Recurse if it's a directory and -r is enabled
                if (file.isDirectory() && recursive) {
                    listFiles(file, depth + 1, output);
                }
            }
        }
    }
}
