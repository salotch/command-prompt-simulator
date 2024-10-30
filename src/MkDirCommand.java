import java.util.ArrayList;
import java.io.File;
public class MkDirCommand implements Command{
    ArrayList<String> current;
   
    public MkDirCommand(ArrayList<String> currentPathList)
    {
        current=currentPathList;
    }
    @Override
    public void execute(String []args)
    {
        if (args.length != 2) {
            System.out.println("Usage: mkdir <directory_name>");
            return;
        }
        String direcortyPath="";
        for (int i = 0; i <current.size(); i++) {
            direcortyPath=direcortyPath.concat(current.get(i)+"\\");
        }
        direcortyPath=direcortyPath.concat(args[1]);
        File dir = new File(direcortyPath);
        if (dir.exists()) {
            System.out.println("Error: Directory " + args[1] + " already exists.");
        }
        else{
        
        dir.mkdir();
        }
    }
}
