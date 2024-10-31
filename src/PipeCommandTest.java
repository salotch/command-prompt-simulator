import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PipeCommandTest {
    private PipeCommand pipeCommand;
    private CLI cli;

    @BeforeEach
    public void setUp() {
        pipeCommand = new PipeCommand();
        cli = new CLI();  // Initialize CLI if needed for your tests
        LsCommand.listFilesOutput = new StringBuilder();  // Reset the output before each test
    }


    @Test
    public void testInvalidUsage() {
        // Prepare to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Execute the command with insufficient arguments
        pipeCommand.execute(new String[] {"ls"});

        // Restore original System.out
        System.setOut(originalOut);

        // Verify that the usage message is printed
        String output = outputStream.toString();
        assertTrue(output.contains("Usage: command1 | command2 ..."), "Should show usage message for invalid input.");
    }

    @Test
    public void testEmptyPipe() {
        // Prepare to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Execute the command with an empty pipe
        pipeCommand.execute(new String[] {"ls", "|"}); 

        // Restore original System.out
        System.setOut(originalOut);

        // Verify that the usage message is printed
        String output = outputStream.toString();
        System.out.println(output);
        assertTrue(output.contains("Usage: command1 | command2 ..."), "Should show usage message for empty pipe.");
    }
    

}
