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
                        String files = listAllFiles();
                        boolean op = false;
                        for (int i = 1; i < cmdArgs.length;  i++){
                            if (cmdArgs[i].contains(">")){
                                redirectToFile(files, cmdArgs[i+1]);
                                op = true;
                                break;
                            }
                        }
                        if(!op)
                            System.out.println(files);

                        System.out.println("Print done\n");
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

}
