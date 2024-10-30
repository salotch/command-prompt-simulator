import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class LsCommandTest {
    private static final String TEST_DIR = "testDir";
    private static final String HIDDEN_FILE = ".hiddenFile";
    private static final String VISIBLE_FILE = "visibleFile";
    private static final String SUB_DIR = "subDir";
    private static final String SUB_FILE = "subFile";

    private LsCommand lsCommand;

    @BeforeEach
    public void setUp() throws IOException {
        lsCommand = new LsCommand();

        // Create test directory and files
        File testDir = new File(TEST_DIR);
        if (!testDir.exists()) testDir.mkdir();

        // Visible file in test directory
        File visibleFile = new File(testDir, VISIBLE_FILE);
        visibleFile.createNewFile();

        // Hidden file in test directory
        File hiddenFile = new File(testDir, HIDDEN_FILE);
        hiddenFile.createNewFile();

        // Set file as hidden on Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            hiddenFile.setReadable(true, false);
            Files.setAttribute(hiddenFile.toPath(), "dos:hidden", true);
        }

        // Create sub-directory and file within it
        File subDir = new File(testDir, SUB_DIR);
        subDir.mkdir();
        File subFile = new File(subDir, SUB_FILE);
        subFile.createNewFile();
    }

    @AfterEach
    public void tearDown() {
        // Delete test files and directory
        deleteDirectoryRecursively(new File(TEST_DIR));
    }

    private void deleteDirectoryRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteDirectoryRecursively(child);
            }
        }
        file.delete();
    }

    @Test
    public void testListFiles() {
        lsCommand.execute(new String[] {"ls", TEST_DIR});
        String output = lsCommand.getOutput();

        assertTrue(output.contains(VISIBLE_FILE), "Visible file should be listed.");
        assertFalse(output.contains(HIDDEN_FILE), "Hidden file should not be listed without -a flag.");
        assertFalse(output.contains(SUB_FILE), "Subdirectory file should not be listed without -r flag.");
    }

    @Test
    public void testListFilesWithHidden() {
        lsCommand.execute(new String[] {"ls", "-a", TEST_DIR});
        String output = lsCommand.getOutput();

        assertTrue(output.contains(VISIBLE_FILE), "Visible file should be listed.");
        assertTrue(output.contains(HIDDEN_FILE), "Hidden file should be listed with -a flag.");
    }

    @Test
    public void testListFilesRecursively() {
        lsCommand.execute(new String[] {"ls", "-r", TEST_DIR});
        String output = lsCommand.getOutput();

        assertTrue(output.contains(VISIBLE_FILE), "Visible file should be listed.");
        assertTrue(output.contains(SUB_FILE), "Subdirectory file should be listed with -r flag.");
    }

    @Test
    public void testListFilesWithHiddenAndRecursively() {
        lsCommand.execute(new String[] {"ls", "-ar", TEST_DIR});
        String output = lsCommand.getOutput();

        assertTrue(output.contains(VISIBLE_FILE), "Visible file should be listed.");
        assertTrue(output.contains(HIDDEN_FILE), "Hidden file should be listed with -a flag.");
        assertTrue(output.contains(SUB_FILE), "Subdirectory file should be listed with -r flag.");

        lsCommand.execute(new String[] {"ls", "-ra", TEST_DIR});
        output = lsCommand.getOutput();

        assertTrue(output.contains(VISIBLE_FILE), "Visible file should be listed.");
        assertTrue(output.contains(HIDDEN_FILE), "Hidden file should be listed with -a flag.");
        assertTrue(output.contains(SUB_FILE), "Subdirectory file should be listed with -r flag.");
    }
}
