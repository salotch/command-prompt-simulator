
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;

public class CatCommandTest {

    private CatCommand catCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private File testFile;
    private File nonExistentFile;

    @BeforeEach
    public void setUp() throws IOException {
       catCommand = new CatCommand();
        System.setOut(new PrintStream(outContent)); // Capture console output for assertions

        // Create a temporary test file with sample content
        testFile = File.createTempFile("testFile", ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(testFile))) {
            writer.println("Line 1");
            writer.println("Line 2");
        }

        // Non-existent file for testing error handling
        nonExistentFile = new File("nonexistentfile.txt");

        // Clear outContent between tests
        outContent.reset();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out); // Restore console output
        System.setIn(System.in); // Restore System.in
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCatWithExistingFile() throws IOException {

        String[] args = {"cat", testFile.getAbsolutePath()};
        assertTrue(testFile.exists(), "Test file should exist");
        catCommand.execute(args);
        String expectedOutput = "Line 1" + System.lineSeparator() + "Line 2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());

    }


    @Test
    public void testCatWithNonExistentFile() {
        String[] args = {"cat", nonExistentFile.getAbsolutePath()};
        catCommand.execute(args);
        String expectedError = "Error: File '" + nonExistentFile.getAbsolutePath() + "' does not exist." + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedError), "Should display an error for a non-existent file.");
    }

    @Test
    public void testCatWithMultipleFiles() throws IOException {
        File anotherTestFile = File.createTempFile("anotherTestFile", ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(anotherTestFile))) {
            writer.println("Another Line 1");
            writer.println("Another Line 2");
        }
        String[] args = {"cat", testFile.getAbsolutePath(), anotherTestFile.getAbsolutePath()};
        catCommand.execute(args);
        String expectedOutput = "Line 1" + System.lineSeparator() + "Line 2" + System.lineSeparator() +
                "Another Line 1" + System.lineSeparator() + "Another Line 2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "Should display contents of both files sequentially.");
        if (anotherTestFile.exists()) {
            anotherTestFile.delete();
        }
    }

    @Test
    public void testCatWithNoArguments() {
        // Arrange
        String[] args = {"cat"};

        // Simulate user input for the readFromUserInput() method
        InputStream originalIn = System.in;
        String simulatedInput = "Test line 1\nTest line 2\nexit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Act
        catCommand.execute(args);

        // Restore System.in
        System.setIn(originalIn);

        // Assert with platform-independent line separator
        String expectedOutput = "Enter text (type 'exit' to finish):" + System.lineSeparator() +
                "You entered:" + System.lineSeparator() +
                "Test line 1" + System.lineSeparator() +
                "Test line 2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "Should display the user input when no files are provided.");
    }
}
