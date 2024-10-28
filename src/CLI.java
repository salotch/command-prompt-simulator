import java.io.*;
import java.util.Scanner;

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
            System.out.println("List of commands: ls, touch, >");

            switch (cmd) {
                case "ls":
                    if (cmdArgs.length > 0 && cmdArgs[0].equals("-a")) {
                        listAllFiles();
                        System.out.println("Print done\n");
                    }
                    break;
                case "touch":
                    if (cmdArgs.length > 0) {
                        createFile(cmdArgs[0]);
                        continue;
                    }
                    break;
                case ">":
                    if (cmdArgs.length > 0) {
                        redirectToFile(cmdArgs[0]);
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

    private static void listAllFiles(){
        File currentDir = new File(".");
        File[] files = currentDir.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Unable to list files.");
        }
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
    private static void redirectToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Output redirected to " + fileName);
            System.out.println("Output written to " + fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
