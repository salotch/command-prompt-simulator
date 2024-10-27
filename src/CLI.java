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

            switch (cmd) {
                case "ls":
                    if (cmdArgs.length > 0 && cmdArgs[0].equals("-a")) {
                        //listAllFiles();
                    }
                    break;
                case "touch":
                    if (cmdArgs.length > 0) {
                        //createFile(cmdArgs[0]);
                    }
                    break;
                case ">":
                    if (cmdArgs.length > 0) {
                        //redirectToFile(cmdArgs[0]);
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
}
