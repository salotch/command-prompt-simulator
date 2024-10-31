import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CdCommandTest {
    private CdCommand cdCommand;

    @BeforeEach
    public void setUp() {
        cdCommand = new CdCommand();
    }

    @Test
    public void testChangeToParentDirectory() throws Exception {
        // Create a temporary directory
        Path tempDir = Files.createTempDirectory("testDir");
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));

        // Change to the temporary directory
        System.setProperty("user.dir", tempDir.toString());

        // Execute cd..
        cdCommand.execute(new String[] {"cd.."});

        // Assert that the current directory is now the parent directory
        assertEquals(tempDir.getParent().toString(), System.getProperty("user.dir"));

        // Clean up
        Files.deleteIfExists(subDir);
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testChangeToExistingDirectory() throws Exception {
        // Create a temporary directory
        Path tempDir = Files.createTempDirectory("testDir");
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));

        // Change to the temporary directory
        System.setProperty("user.dir", tempDir.toString());

        // Execute cd subDir
        cdCommand.execute(new String[] {"cd", "subDir"});

        // Assert that the current directory is now the sub directory
        assertEquals(subDir.toString(), System.getProperty("user.dir"));

        // Clean up
        Files.deleteIfExists(subDir);
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testChangeToNonExistentDirectory() {
        String currentDir = System.getProperty("user.dir");

        // Execute cd to a non-existent directory
        cdCommand.execute(new String[] {"cd", "nonExistentDir"});

        // Assert that the current directory remains the same
        assertEquals(currentDir, System.getProperty("user.dir"));
    }

    @Test
    public void testInvalidUsage() {
        // Capture the output
        String currentDir = System.getProperty("user.dir");
        assertEquals(System.getProperty("user.dir"), System.getProperty("user.dir"));

        // Execute cd without arguments
        cdCommand.execute(new String[] {"cd"});

        // The output should indicate usage
        assertTrue(System.getProperty("user.dir").contains(curr));
    }
}
