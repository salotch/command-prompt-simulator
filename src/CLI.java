import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class CLI {
    private final Map<String, Command> commandMap;
    public static ArrayList<String> currentPathList;

    public static void setPath(String newPath) {
        // Use the OS-specific separator but escape if on Windows for regex
        String separator = File.separator.equals("\\") ? "\\\\" : File.separator;

        // Split the newPath based on the OS-specific separator
        String[] pathComponents = newPath.split(separator);

        // Convert the array to an ArrayList and store it in currentPathList
        currentPathList = new ArrayList<>(Arrays.asList(pathComponents));
    }


    public CLI() {
        setPath(new File("").getAbsolutePath());// the path as a string of the code in your PC.

        commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand());
        commandMap.put("ls", new LsCommand(currentPathList));  // Pass currentPathList to LsCommand
        commandMap.put("touch", new TouchCommand());
        commandMap.put("rmdir", new RmdirCommand());
        commandMap.put("cat", new CatCommand());
        commandMap.put("cd", new CdCommand());
        commandMap.put("mkdir", new MkDirCommand(currentPathList));
        // Add other commands here
    }

    public void executeCommand(String input) {
        String[] parts = input.split(" ");
        String commandName = parts[0];
        Command command = commandMap.get(commandName);

        if (command == null) {
            System.out.println("Command not found");
            return;
        }
        String[] commandArgs;

        // Check for redirection operators
        if (input.contains(">") || input.contains(">>")) {
            boolean append = input.contains(">>");

            // Ensure there's a filename after the redirection operator
            if (parts.length < 3) {
                System.out.println("Error: No filename provided after redirection operator.");
                return;  // Exit the method, prompting the user to enter a new command
            }

            String fileName = parts[parts.length - 1];

            // Filter out the redirection symbol and filename
            commandArgs = new String[parts.length - 2];
            System.arraycopy(parts, 0, commandArgs, 0, parts.length - 2);

            command = new RedirectionCommand(command, fileName, append);
            command.execute(commandArgs);
        }else {
            // Pass all parts (including arguments and flags) to `execute` method in Command
            command.execute(parts);
        }

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI. Type 'help' to show available commands and their usage, Type 'exit' to quit.");
        while (true) {
            for (int i = 0; i < currentPathList.size(); i++) {
                System.out.print(currentPathList.get(i));
                if(i!=currentPathList.size()-1)
                    System.out.print("\\");
                else
                    System.out.print("> ");
            }

            
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting CLI.");
                break;
            }

            if ("help".equalsIgnoreCase(input)){
                showHelp();
            }

            executeCommand(input);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.start();
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
