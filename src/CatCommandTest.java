import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatCommandTest {

    private CatCommand catCommand;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        catCommand = new CatCommand();
        System.setOut(new PrintStream(outputStream)); // Redirect System.out to capture print statements
    }

    @AfterEach
    public void tearDown() {
        // Clean up files created during testing
        File testFile1 = new File("testfile1.txt");
        File testFile2 = new File("testfile2.txt");
        if (testFile1.exists()) testFile1.delete();
        if (testFile2.exists()) testFile2.delete();

        System.setOut(System.out); // Reset standard output
        outputStream.reset(); // Clear output stream after each test
    }

    @Test
    public void testDisplayFileContents() throws IOException {
        outputStream.reset(); // Clear any previous output

        // Arrange
        File testFile = new File("testfile1.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Hello, world!\nThis is a test file.");
        }

        String[] args = {"cat", "testfile1.txt"};

        // Act
        catCommand.execute(args);

        // Assert
        String output = outputStream.toString();
        System.out.println("Output for testDisplayFileContents: \n" + output); // Show output in console
        assertTrue(output.contains("Contents of testfile1.txt:"), "Output should contain file header.");
        assertTrue(output.contains("Hello, world!"), "Output should contain the first line of the file.");
        assertTrue(output.contains("This is a test file."), "Output should contain the second line of the file.");
    }

    @Test
    public void testFileDoesNotExist() {
        outputStream.reset(); // Clear any previous output

        // Arrange
        String[] args = {"cat", "nonexistent.txt"};

        // Act
        catCommand.execute(args);

        // Assert
        String output = outputStream.toString();
        System.out.println("Output for testFileDoesNotExist: \n" + output); // Show output in console
        assertTrue(output.contains("Error: File 'nonexistent.txt' does not exist."), "Output should indicate the file does not exist.");
    }

    @Test
    public void testNoFilenameProvided() {
        outputStream.reset(); // Clear any previous output

        // Arrange
        String[] args = {"cat"};

        // Act
        catCommand.execute(args);

        // Assert
        String output = outputStream.toString();
        System.out.println("Output for testNoFilenameProvided: \n" + output); // Show output in console
        assertTrue(output.contains("Usage: cat <file_name1> <file_name2> ..."), "Output should show usage information.");
    }
}
