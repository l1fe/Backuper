import FileManager.FileManager;
import shell.Shell;
import shell.ShellState;
import shell.commands.CdCommand;
import shell.commands.DirCommand;
import shell.commands.ExitCommand;
import shell.commands.PwdCommand;

/**
 * Created by inaumov on 15.07.2014.
 */

public class Main {
    public static void main(String[] args) {
        Shell<ShellState> shell = new Shell<ShellState>();

        ShellState shellState = new ShellState();
        FileManager fileManager = new FileManager();
        shellState.fileManager = fileManager;

        shell.setState(shellState);

        shell.addCommand(new ExitCommand());
        shell.addCommand(new DirCommand());
        shell.addCommand(new CdCommand());
        shell.addCommand(new PwdCommand());

        shell.run();
    }
}
