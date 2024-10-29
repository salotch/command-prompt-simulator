import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
public class CLI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the CLI! Type 'help' for a list of commands.");

        while (true) {
            System.out.print("> ");
            command = input.nextLine().trim();

            // Split command and arguments
            String[] parts = command.split(" ");
            String cmd = parts[0];
            String[] cmdArgs = new String[parts.length - 1];
            System.arraycopy(parts, 1, cmdArgs, 0, cmdArgs.length);

            switch (cmd) {
                case "ls":
                    ls();

                    break;
                case "rmdir":
                    if (cmdArgs.length > 0) {
                        removeDirectory(cmdArgs[0]);
                    } else {
                        System.out.println("Usage: rmdir <directory_name>");
                    }
                    break;
                case "help":
                    showHelp();
                    break;
                case "cat":
                    if (cmdArgs.length > 0) {
                        cat(cmdArgs);
                    } else {
                        System.out.println("Usage: cat <file1> <file2> ...");
                    }
                    break;
                case "touch":
                    if (cmdArgs.length > 0) {
                        createFile(cmdArgs[0]);
                        continue;
                    }
                    break;
                case "exit":
                    System.out.println("Exiting CLI...");
                    return;
                default:
                    System.out.println("Command not recognized. Type 'help' for assistance.");
            }
        }
    }

    // Method for ls -a that returns all files (including hidden ones) as a string
    public static String listAllFiles() {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles();
        if (files == null) return "";

        StringBuilder fileNames = new StringBuilder();
        for (File file : files) {
            fileNames.append(file.getName()).append("\n");
        }
        return fileNames.toString();
    }

    private static void createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                System.out.println("File timestamp updated.");
            } else {
                if (file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    System.out.println("Failed to create file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to redirect output to a file
    public static boolean redirectToFile(String content, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
    public static void ls() {
        // Get the current directory
        File currentDir = new File(".");

        // List all files in the current directory
        File[] files = currentDir.listFiles();

        if (files == null) {
            System.out.println("Could not list files.");
            return;
        }

        // Sort files alphabetically
        Arrays.sort(files);

        // Display each non-hidden file name
        for (File file : files) {
            if (!file.isHidden()) { // Skip hidden files
                System.out.println(file.getName());
            }
        }
    }
    public static void removeDirectory(String directoryName) {
        File dir = new File(directoryName);

        // Check if the directory exists
        if (!dir.exists()) {
            System.out.println("Error: Directory '" + directoryName + "' does not exist.");
            return;
        }

        // Check if it is actually a directory
        if (!dir.isDirectory()) {
            System.out.println("Error: '" + directoryName + "' is not a directory.");
            return;
        }

        // Check if the directory is empty
        String[] files = dir.list();
        if (files != null && files.length > 0) {
            System.out.println("Error: Directory '" + directoryName + "' is not empty.");
            return;
        }

        // Try to delete the empty directory
        if (dir.delete()) {
            System.out.println("Directory removed successfully.");
        } else {
            System.out.println("Failed to remove directory.");
        }
    }
    public static void cat(String[] fileNames) {
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
    public static void showHelp() {
        System.out.println("Available commands:");
        System.out.println("pwd       - Print the current working directory.");
        System.out.println("cd <dir>  - Change the directory to <dir>.");
        System.out.println("ls        - List files and directories in the current directory.");
        System.out.println("ls -a     - List all files, including hidden ones, in the current directory.");
        System.out.println("ls -r     - List files in reverse alphabetical order.");
        System.out.println("mkdir <dir> - Create a new directory named <dir>.");
        System.out.println("rmdir <dir> - Remove an empty directory named <dir>.");
        System.out.println("touch <file> - Create a new file named <file> or update its timestamp.");
        System.out.println("mv <src> <dest> - Move or rename a file from <src> to <dest>.");
        System.out.println("rm <file> - Delete the specified file.");
        System.out.println("cat <file1> <file2> ... - Display contents of files in sequence.");
        System.out.println("> <file> - Redirect output to a file, overwriting the file.");
        System.out.println(">> <file> - Append output to a file.");
        System.out.println("| - Pipe the output of one command as input to another command.");
    }

}
