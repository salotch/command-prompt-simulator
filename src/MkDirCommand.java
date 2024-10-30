public class MkDirCommand implements Command{
    
    @Override
    public void execute(String []args)
    {

        if (args.length < 2) {
            System.out.println("Usage: mkdir <directory_name>");
            return;
        }

        // Loop through all provided directory names, starting from index 1
        for (int i = 1; i < args.length; i++) {
            String directoryName = args[i];
            
        }
        
    }
}
