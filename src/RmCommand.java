import java.io.File;
public class RmCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            System.out.println("Usage:rm <fileName.extension> [<fileName.extension> ...]");
            return;
        } else {
            for (int i = 1; i < args.length; i++) {
                File file = new File(args[i]);
                System.out.println(file.getName());
                if (file.isFile()) {
                    if (file.delete()) {
                        System.out.println();
                    } else {
                        System.out.println("Usage:rm <fileName.extension> [<fileName> ...]");
                        return;
                    }

                }else if(file.isDirectory()) {
                    System.out.println("rm: cannot remove " + args[i] + ": Is a directory");
                }
                else{
                    System.out.println("Usage:rm <fileName.extension> [<fileName.extension> ...]");
                }
            }

        }

    }
}
