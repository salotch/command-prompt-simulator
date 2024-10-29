import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CLIUtils {

    public static String captureOutput(Command command, String[] args) {
        // Backup the original System.out
        PrintStream originalOut = System.out;

        // Create a stream to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream captureStream = new PrintStream(outputStream);

        try {
            // Redirect System.out to captureStream
            System.setOut(captureStream);

            // Execute the command
            command.execute(args);

            // Flush the stream and return captured output as a string
            captureStream.flush();
            return outputStream.toString();
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}

