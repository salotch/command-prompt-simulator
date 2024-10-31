import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MvCommand implements Command{
    public void execute(String[] args) {
        String currentPath =System.getProperty("user.dir");
        if(args.length==3){
            File f1 =new File(args[1]); 
            File f2 =new File(args[2]); 
            Boolean typeF1 =f1.isFile();
            Boolean typeF2 =f2.isDirectory();
            if (!f1.exists())
            {
                System.out.println("The file does not exist");
                return;
            }
            if(typeF2){
                Path path1 =f1.toPath();
                Path path2 =f2.toPath().resolve(f1.getName());
                try {
                    Files.move(path1,path2,StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return;
            }
            
            if(!f1.isAbsolute() && !f2.isAbsolute()){
                if(f2.exists() && (typeF1 == typeF2)){
                    System.out.println("The file that you want to rename it is already exist");
                    return;
                }
                f1 =new File(currentPath,args[1]);
                f2 =new File(currentPath,args[2]);
                    f1.renameTo(f2);
            }

        }
        else if(args.length>3){
            File f2 =new File(currentPath,args[args.length-1]); 
            Boolean typeF2 =f2.isDirectory();
            System.out.println(typeF2);
            if(typeF2){
                if(!f2.exists()){
                    System.out.println("Directory not found ");
                    return;
                }
            else{
                for(int i=1;i<args.length-1;i++){
                    File f =new File(currentPath,args[i]); 
                    if(!f.exists()){
                        System.out.println(f.getName()+" not found");
                        continue;
                    }
                    else{
                        Path path1 =f.toPath();
                Path path2 =f2.toPath().resolve(f.getName());
                try {
                    Files.move(path1,path2,StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                    }
                }
            }
            }
        }
    }
    
}
