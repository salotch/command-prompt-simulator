import java.io.File;

public class CdCommand implements Command {
    @Override
    public void execute(String[] args) {

        // Check if the directory name is provided or it is cd..
        if (args.length == 1 && args[0].equals("cd..")) {
            String cuurentDir = System.getProperty("user.dir");
            File currDirFile = new File(cuurentDir);
            File parentOfCurrDir = currDirFile.getParentFile();
            if (parentOfCurrDir != null) {
                System.setProperty("user.dir", parentOfCurrDir.getAbsolutePath());
                System.out.println();
            }
        } else if (args.length == 2) {
            // Get the target directory from the arguments
            String dirCurrent = System.getProperty("user.dir");
            String targetDir = args[1];
            File newDir;
            if (targetDir.indexOf(":") != -1) {
                // System.out.println(targetDir);
                newDir = new File(targetDir);
            } else {
                // System.out.println(dirCurrent + "\\" + targetDir);
                newDir = new File(dirCurrent + "\\" + targetDir);
            }

            // Check if the target directory exists
            if (!newDir.exists() || !newDir.isDirectory()) {
                System.out.println("Error: Directory \"" + targetDir + "\" does not exist or is not a directory.");
                return;
            }

            // Change the working directory (this only affects the Java program)
            System.setProperty("user.dir", newDir.getAbsolutePath());

            // Output the current directory
            System.out.println("Changed directory to: " + System.getProperty("user.dir"));

        } else {
            System.out.println("Usage: cd <directory>");
            return;
        }
    }
}
