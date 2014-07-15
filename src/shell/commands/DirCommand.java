package shell.commands;

import shell.AbstractCommand;
import shell.ShellState;
import shell.ShellUtils;

public class DirCommand extends AbstractCommand<ShellState> {
    public DirCommand() {
        super("dir", 0);
    }

    public void execute(String argumentsLine, ShellState shellState) {
        String[] args = ShellUtils.parseCommandArgsLine(argumentsLine);
        ShellUtils.checkArgumentsNumber(this, args.length);

        String currentDir = shellState.fileManager.getCurrentDirectory();
        System.out.println("Current directory:\n" + currentDir);
        System.out.println("Current directory content:");
        String[] dirContent = shellState.fileManager.getCurrentDirectoryContent();
        for (final String entry : dirContent) {
            System.out.println(entry);
        }
    }
}
