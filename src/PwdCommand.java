

public class PwdCommand implements Command {
    public String execute(String []args){
        String currentDir = System.getProperty("user.dir");
        
        System.out.println(currentDir);
        return currentDir;
    }
}

