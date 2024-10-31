

public class PwdCommand implements Command {
    public void execute(String []args){
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
    }
}

