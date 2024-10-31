import java.io.File;

public class RmdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: rmdir <directory_name1> <directory_name2> ...");
            return;
        }

        // Loop through all provided directory names, starting from index 1
        for (int i = 1; i < args.length; i++) {
            String directoryName = args[i];
            removeDirectory(directoryName);
        }
    }
    private void removeDirectory(String directoryName) {
        String cuurentDir = System.getProperty("user.dir");
        File dir = new File(cuurentDir+"\\"+directoryName);

        // Check if the directory exists
        if (!dir.exists()) {
            System.out.println("Error: Directory '" + directoryName + "' does not exist.");
            return;
        }

        // Check if it is actually a directory
        if (!dir.isDirectory()) {
            System.out.println("Error: '" + directoryName + "' is not a directory.");
            return;
        }

        // Check if the directory is empty
        String[] files = dir.list();
        if (files != null && files.length > 0) {
            System.out.println("Error: Directory '" + directoryName + "' is not empty.");
            return;
        }

        // Try to delete the empty directory
        if (dir.delete()) {
            System.out.println("Directory removed successfully.");
        } else {
            System.out.println("Failed to remove directory.");
        }
    }
}
