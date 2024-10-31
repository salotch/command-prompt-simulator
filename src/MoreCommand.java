import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoreCommand implements Command {
    @Override
    public void execute(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: more <file1> <file2> ... <fileN>");
            return;
        }
        int linesPerPage = 10; // Number of lines to display per page
        ArrayList<String> Names;
        int lineCount = 0;
        // Read each file and count occurrences of each line
        for (int i = 1; i < args.length; i++) {

            File file = new File(args[i]);
            if (file.isDirectory()) {
                System.out.println("Error: '" + args[i] + "' is not a file.");
                continue; // Skip to the next file if this one doesn't exist
            } else {
                System.out.println(args[i]);
                lineCount++;

                if (lineCount == linesPerPage) {
                    System.out.print("Press Enter to continue...");
                    new Scanner(System.in).nextLine(); // Wait for user input
                    lineCount = 0; // Reset line count for the next page
                }
            }
        }

    }

}