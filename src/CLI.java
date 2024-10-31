import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    private final Map<String, Command> commandMap;
    
    public CLI() {
        commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand());
        commandMap.put("ls", new LsCommand());
        commandMap.put("touch", new TouchCommand());
        commandMap.put("rmdir", new RmdirCommand());
        commandMap.put("cat", new CatCommand());
        commandMap.put("cd", new CdCommand());
        commandMap.put("rm", new RmCommand());
        commandMap.put("cd..", new CdCommand());
        commandMap.put("mkdir", new MkDirCommand());
        commandMap.put("more", new MoreCommand());
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

        // Check for redirection operators
        if (input.contains(">") || input.contains(">>")) {
            boolean append = input.contains(">>");
            String fileName = parts[parts.length - 1];
            command = new RedirectionCommand(command, fileName, append);
        }
        // Pass all parts (including arguments and flags) to `execute` method in Command
        command.execute(parts);

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Welcome to the CLI. Type 'help' to show available commands and their usage, Type 'exit' to quit.");
        while (true) {
            String dir = System.getProperty("user.dir");// i put it in the loop to update the location in the consol
                                                        // every time
            System.out.print(dir + "> ");

            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting CLI.");
                break;
            }

            if ("help".equalsIgnoreCase(input)) {
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
