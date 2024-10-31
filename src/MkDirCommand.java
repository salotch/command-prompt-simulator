import java.io.File;

public class MkDirCommand implements Command {

    @Override
    public void execute(String[] args) {
        // Check if any arguments are provided
        if (args.length <= 1) {
            System.out.println("Usage:mkdir <directory1> [<directory2> ...]");
            return;
        }
        String directorypath =System.getProperty("user.dir");
        for (int i=1;i<args.length;i++) {
            // Create a File object for the directory
            File dir = new File(directorypath+"\\"+args[i]);

            // Check if the directory already exists
            if (dir.exists()) {
                System.out.println("Error: Directory \"" + args[i] + "\" already exists.");
            } else {
                // Create the directory (and any necessary parent directories)
                boolean created = dir.mkdirs();
                if (created) {
                    System.out.println();
                } else {
                    System.out.println("Error: Unable to create directory \"" + args[i] + "\".");
                }
            }
        }
    }
}
