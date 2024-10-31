import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LsCommand implements Command {
    private boolean showHidden = false;
    private boolean recursive = false;
    private boolean reverse = false;
    public static boolean pipe;
    private String directoryPath;
    public static StringBuilder listFilesOutput; // for pipe command
    private final StringBuilder output = new StringBuilder(); // Store output for testing

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
        directoryPath =System.getProperty("user.dir");
        parseArguments(args);  // Parse any flags or directory path arguments
        File directory = new File(directoryPath);


        if (!directory.isDirectory()) {
            output.append("Error: ").append(directoryPath).append(" is not a directory.\n");
            System.out.print(output);
            return;
        }

        // List files with recursive option if required
        listFiles(directory, 0, output);

        if (pipe) {
            return;
        }
        else
            System.out.print(output);

        // Reset flags and directory path for the next command execution
        showHidden = false;
        recursive = false;
        reverse = false;
        directoryPath = System.getProperty("user.dir") ; // Reset to default path
    }

    private void parseArguments(String[] args) {
        for (String arg : args) {
            if (arg.equals("-a")) {
                showHidden = true;

            }else if(arg.equals("-r")){
                reverse=true;
            }
            else if (arg.equals("-R")) {
                recursive = true;
            } else if (arg.equals("-Ra") || arg.equals("-aR")) {  // For both recursive and hidden
                showHidden = true;
                recursive = true;
            } else if (!arg.equals(">") && !arg.equals(">>") && !arg.equals("|") && !arg.equals("ls")) {
                // Assume it’s a directory path if it's not a flag or redirection
                directoryPath = arg;
            } else if (args.equals("|")) {
                pipe = true;
            }
        }
    }

    private void listFiles(File directory, int depth, StringBuilder output) {
        String indent = "  ".repeat(depth);  // Indentation for recursive display
        File[] files = directory.listFiles();
        if (files == null) {
            output.append("Unable to access directory: ").append(directory.getPath()).append("\n");
            return;
        }if(reverse){
            for(int i= files.length -1;i>=0;--i){
                if (showHidden || !files[i].isHidden()) {
                    output.append(indent).append(files[i].getName()).append("\n");
                    if (files[i].isDirectory() && recursive) {
                        listFiles(files[i], depth + 1, output);
                    }
                }
            }
        } else {
            for (File file : files) {
                if (showHidden || !file.isHidden()) {
                    output.append(indent).append(file.getName()).append("\n");

                    // Recurse if it's a directory and -r is enabled
                    if (file.isDirectory() && recursive) {
                        listFiles(file, depth + 1, output);
                    }
                }
            }
            listFilesOutput = output;
        }
    }
}
