import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RedirectionCommandTest {

    private static final String TEST_FILE = "testOutput.txt";
    private Command sampleCommand;

    @BeforeEach
    void setUp() {
        // A sample command to produce output (could be `EchoCommand` or a mock command)
        sampleCommand = args -> System.out.print("Sample output");  // Example output
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test file after each test
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void testOverwriteRedirection() throws IOException {
        // Run redirection with overwrite mode (>)
        RedirectionCommand overwriteCommand = new RedirectionCommand(sampleCommand, TEST_FILE, false);
        overwriteCommand.execute(new String[]{});

        // Read the file content and verify it matches expected output
        String content = Files.readString(Path.of(TEST_FILE));
        assertEquals("Sample output", content, "File should contain 'Sample output' after overwrite.");
    }

    @Test
    void testAppendRedirection() throws IOException {
        // Initial write to file to set up the initial state
        Files.writeString(Path.of(TEST_FILE), "Initial content\n");

        // Run redirection with append mode (>>)
        RedirectionCommand appendCommand = new RedirectionCommand(sampleCommand, TEST_FILE, true);
        appendCommand.execute(new String[]{});

        // Read the file content and verify it matches expected appended output
        String content = Files.readString(Path.of(TEST_FILE));
        assertEquals("Initial content\nSample output", content, "File should append 'Sample output' to 'Initial content'");
    }
    @Test
    void testMissingFilenameRedirection() {
        // Initialize the outputStreamCaptor before executing the command
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStreamCaptor));  // Capture output for testing

        try {
            // Simulate command input without a filename
            CLI cli = new CLI();
            cli.executeCommand("cat >");  // Missing filename after >

            // Verify that the error message was printed
            assertTrue(outputStreamCaptor.toString().contains("Error: No filename provided after redirection operator."),
                    "Should prompt an error when no filename is provided after redirection operator.");
        } finally {
            // Restore original System.out after the test
            System.setOut(originalOut);
        }
    }


}
