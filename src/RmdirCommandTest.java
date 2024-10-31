import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;

public class RmdirCommandTest {

    private RmdirCommand rmdirCommand;
    private File emptyDir;
    private File nonEmptyDir;
    private File file;

    @BeforeEach
    public void setUp() throws IOException {
        rmdirCommand = new RmdirCommand();

        // Create an empty directory
        emptyDir = new File("emptyDir");
        if (!emptyDir.exists()) {
            emptyDir.mkdir();
        }

        // Create a non-empty directory
        nonEmptyDir = new File("nonEmptyDir");
        if (!nonEmptyDir.exists()) {
            nonEmptyDir.mkdir();
        }
        File tempFile = new File(nonEmptyDir, "tempFile.txt");
        Files.createFile(tempFile.toPath());

        // Create a regular file
        file = new File("regularFile.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @AfterEach
    public void tearDown() {
        if (file.exists()) file.delete();
        if (emptyDir.exists()) emptyDir.delete();

        // Delete nonEmptyDir and its contents
        File tempFile = new File(nonEmptyDir, "tempFile.txt");
        if (tempFile.exists()) tempFile.delete();
        if (nonEmptyDir.exists()) nonEmptyDir.delete();
    }

    @Test
    public void testRemoveEmptyDirectory() {
        String[] args = {"rmdir", emptyDir.getName()};
        rmdirCommand.execute(args);
        //check if the empty directory is still there or not and if its not then print the massage
        assertFalse(emptyDir.exists(), "The empty directory should be removed successfully.");
    }


    @Test
    public void testRemoveNonExistentDirectory() {
        String nonExistentDirName = "nonExistentDir";
        String[] args = {"rmdir", nonExistentDirName};

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        try {
            rmdirCommand.execute(args);

            // Flush to capture all output
            System.err.flush();

            String expectedMessage = "Error: Directory '" + nonExistentDirName + "' does not exist.";
            assertFalse(errContent.toString().contains(expectedMessage), "Should display an error for non-existent directory.");
        } finally {
            System.setErr(originalErr);
        }
    }


    @Test
    public void testRemoveNonEmptyDirectory() {
        String[] args = {"rmdir", nonEmptyDir.getName()};

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        try {
            rmdirCommand.execute(args);

            System.err.flush();
            assertTrue(nonEmptyDir.exists(), "Should display an error for a non-empty directory.");
        } finally {
            System.setErr(originalErr);
        }
    }



    @Test
    public void testRemoveFileInsteadOfDirectory() {
        String[] args = {"rmdir", file.getName()};
        rmdirCommand.execute(args);
        System.err.flush();
        assertTrue(file.exists(),"Should display the correct error message for a file instead of a directory.");

    }



    @Test
    public void testNoArgumentsProvided() {
        String[] args = {"rmdir"};
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        rmdirCommand.execute(args);
        System.err.flush();
        String expectedMessage = "Usage: rmdir <directory_name1> <directory_name2> ...\r\n";
        assertEquals(expectedMessage, outputStreamCaptor.toString());

    }



}
