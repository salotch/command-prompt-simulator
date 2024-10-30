public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        CLI.showHelp();
    }
}
