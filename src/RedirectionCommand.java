import java.io.FileWriter;
import java.io.IOException;

public class RedirectionCommand implements Command {
    private final Command command;
    private final String fileName;
    private final boolean append;

    public RedirectionCommand(Command command, String fileName, boolean append) {
        this.command = command;
        this.fileName = fileName;
        this.append = append;
    }

    @Override
    public void execute(String[] args) {
        // Capture the output of the command
        String output = CLIUtils.captureOutput(command, args);

        // Write the captured output to the file
        try (FileWriter writer = new FileWriter(fileName, append)) {
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

