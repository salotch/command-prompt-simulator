public class CdCommand implements Command {
    public void simpleMessage(){
        System.out.println("hi");
    }

    @Override
    public void execute(String[] args) {

        System.out.println("hi salma");
    }  
}
