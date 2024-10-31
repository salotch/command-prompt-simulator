import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;

class CatCommandTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Capture System.out output to verify console output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    void testSingleFileOutput() throws IOException {
        // Create a sample file with test data
        String fileName = "testFile1.txt";
        String fileContent = "This is a test file.";
        Files.writeString(Path.of(fileName), fileContent);

        try {
            // Run the command with the test file
            CatCommand catCommand = new CatCommand();
            catCommand.execute(new String[] {"cat", fileName});

            // Verify the output matches the content of the test file
            assertEquals(fileContent, outputStreamCaptor.toString().trim(), "Output should match the file content.");
        } finally {
            // Clean up the test file
            Files.deleteIfExists(Path.of(fileName));
        }
    }

    @Test
    void testMultipleFileOutput() throws IOException {
        // Create multiple sample files with test data
        String file1 = "testFile1.txt";
        String file2 = "testFile2.txt";
        String content1 = "File 1 content";
        String content2 = "File 2 content";
        Files.writeString(Path.of(file1), content1);
        Files.writeString(Path.of(file2), content2);

        try {
            // Run the command with multiple files
            CatCommand catCommand = new CatCommand();
            catCommand.execute(new String[] {"cat", file1, file2});

            // Verify the output matches the concatenated content of both files
            String expectedOutput = content1 + System.lineSeparator() + content2;
            assertEquals(expectedOutput, outputStreamCaptor.toString().trim(), "Output should match concatenated content of both files.");
        } finally {
            // Clean up the test files
            Files.deleteIfExists(Path.of(file1));
            Files.deleteIfExists(Path.of(file2));
        }
    }

    @Test
    void testNonExistentFile() {
        // Run the command with a non-existent file
        CatCommand catCommand = new CatCommand();
        String nonExistentFile = "nonExistentFile.txt";
        catCommand.execute(new String[] {"cat", nonExistentFile});

        // Verify the output contains an error message
        assertTrue(outputStreamCaptor.toString().contains("Error: File '" + nonExistentFile + "' does not exist."),
                "Should output error message for non-existent file.");
    }

    @Test
    void testReadFromUserInput() {
        // Simulate user input with System.setIn
        String simulatedInput = "User input line 1\nUser input line 2\nexit\n";
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            // Run the command without file arguments to read from user input
            CatCommand catCommand = new CatCommand();
            catCommand.execute(new String[] {"cat"});

            // Verify the output matches the simulated user input
            String expectedOutput = "User input line 1" + System.lineSeparator() +
                    "User input line 2" + System.lineSeparator();
            assertEquals(expectedOutput, outputStreamCaptor.toString(), "Output should match simulated user input.");
        } finally {
            // Restore original System.in
            System.setIn(originalIn);
        }
    }
}
