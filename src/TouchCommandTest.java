import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import org.junit.jupiter.api.*;
        import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TouchCommandTest {

    private TouchCommand touchCommand;

    @BeforeEach
    public void setUp() {
        touchCommand = new TouchCommand();
    }

    @AfterEach
    public void tearDown() {
        // Clean up files created during testing
        File testFile = new File("testfile.txt");
        if (testFile.exists()) {
            testFile.delete();
        }
        File existingFile = new File("existingfile.txt");
        if (existingFile.exists()) {
            existingFile.delete();
        }
    }

    @Test
    public void testCreateNewFile() {
        // Arrange
        String[] args = {"testfile.txt"};

        // Act
        touchCommand.execute(args);

        // Assert
        File file = new File("testfile.txt");
        assertTrue(file.exists(), "File should be created by the touch command.");
    }

    @Test
    public void testUpdateTimestampForExistingFile() throws InterruptedException {
        // Arrange
        File existingFile = new File("existingfile.txt");
        try {
            existingFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Setup error: Could not create existing file.");
        }

        long originalTimestamp = existingFile.lastModified();
        String[] args = {"existingfile.txt"};

        // Wait to ensure timestamp changes
        Thread.sleep(1000);

        // Act
        touchCommand.execute(args);

        // Assert
        long updatedTimestamp = existingFile.lastModified();
        assertTrue(updatedTimestamp > originalTimestamp, "Timestamp should be updated for the existing file.");
    }

    @Test
    public void testNoFilenameProvided() {
        // Arrange
        String[] args = {};

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> touchCommand.execute(args), "Touch command should handle empty input without error.");
    }
}
