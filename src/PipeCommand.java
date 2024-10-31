import java.io.*;

public class PipeCommand implements Command {
    CLI cli;
    boolean pipfirst = false;

    @Override
    public void execute(String[] args) {
        cli = new CLI();
        if (args.length < 3) {
            System.out.println("Usage: command1 | command2 ...");
            return;
        } else {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (!args[i].equals("|") && i % 2 == 0) {
                    if (args[i].toLowerCase().equals("ls")) {
                        LsCommand.pipe = true;
                        pipfirst = true;
                        cli.executeCommand(args[i]);
                        output = new StringBuilder(LsCommand.listFilesOutput);
                        // System.out.println(output);
                        LsCommand.listFilesOutput.setLength(0);
                        LsCommand.pipe = false;
                    } else if ((args[i].equals("more") || args[i].equals("unique")) && pipfirst) {

                        String result = output.toString().replaceAll("[\r\n]+", " ");
                        
                        args[i] = args[i].concat(" " + result);
                        cli.executeCommand(args[i]);
                        pipfirst = false;
                    } else {
                        System.out.println("Usage: command1 | command2 ...");
                        return;
                    }
                } else if (args[i].equals("|") && i % 2 == 1) {
                    continue;
                } else {
                    System.out.println("Usage: command1 | command2 ...");
                    return;
                }
            }

        }
    }
    
}
