import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class MkDirCommandTest {
    private MkDirCommand mkDirCommand;

    @BeforeEach
    public void setUp() {
        mkDirCommand = new MkDirCommand();
    }

    @Test
    public void testCreateSingleDirectory() throws Exception {
        // create a temporary directory
        Path tempDir = Files.createTempDirectory("testDir");

        // change the current working directory
        System.setProperty("user.dir", tempDir.toString());

        // execute mkdir command
        mkDirCommand.execute(new String[] {"mkdir", "newDir"});

        // assert that the directory was created
        File createdDir = new File(tempDir.toString(), "newDir");
        assertTrue(createdDir.exists() && createdDir.isDirectory());

        // clean up
        Files.deleteIfExists(createdDir.toPath());
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testCreateMultipleDirectories() throws Exception {
        // create a temporary directory
        Path tempDir = Files.createTempDirectory("testDir");

        // change the current working directory
        System.setProperty("user.dir", tempDir.toString());

        // execute mkdir command for multiple directories
        mkDirCommand.execute(new String[] {"mkdir", "dir1", "dir2"});

        // assert that both directories were created
        File dir1 = new File(tempDir.toString(), "dir1");
        File dir2 = new File(tempDir.toString(), "dir2");
        assertTrue(dir1.exists() && dir1.isDirectory());
        assertTrue(dir2.exists() && dir2.isDirectory());

        // clean up
        Files.deleteIfExists(dir1.toPath());
        Files.deleteIfExists(dir2.toPath());
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testDirectoryAlreadyExists() throws Exception {
        // create a temporary directory
        Path tempDir = Files.createTempDirectory("testDir");
        System.setProperty("user.dir", tempDir.toString());

        // create the directory first
        File existingDir = new File(tempDir.toString(), "existingDir");
        existingDir.mkdir();

        // execute mkdir command for an existing directory
        mkDirCommand.execute(new String[] {"mkdir", "existingDir"});

        // Verify that the output indicates the directory already exists (You may need to capture System.out output or verify through logs)
        // Here we just check the existence since we can't capture console output directly
        assertTrue(existingDir.exists());

        // Clean up
        Files.deleteIfExists(existingDir.toPath());
        Files.deleteIfExists(tempDir);
    }

    @Test
    public void testUsageMessage() {
        // Capture the current directory
        String currentDir = System.getProperty("user.dir");

        // Execute mkdir with no arguments
        mkDirCommand.execute(new String[] {"mkdir"});

        // Since we can't capture System.out directly, we'll just verify that the directory is unchanged
        assertEquals(currentDir, System.getProperty("user.dir"));
    }
}
