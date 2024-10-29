import java.io.*;
public class PipeCommand implements Command {
    private final Command firstCommand;
    private final Command secondCommand;

    public PipeCommand(Command firstCommand, Command secondCommand) {
        this.firstCommand = firstCommand;
        this.secondCommand = secondCommand;
    }

    @Override
    public void execute(String[] args) {
        String output = CLIUtils.captureOutput(firstCommand, args);
        secondCommand.execute(new String[] {output}); // Pass the output as input to second command
    }
}
