package shell.commands;

import shell.AbstractCommand;
import shell.ShellState;
import shell.ShellUtils;

public class CdCommand extends AbstractCommand<ShellState> {
    public CdCommand() {
        super("cd", 1);
    }

    public void execute(String argumentsLine, ShellState shellState) {
        String[] args = ShellUtils.parseCommandArgsLine(argumentsLine);
        ShellUtils.checkArgumentsNumber(this, args.length);
        String newPath = args[0];
        shellState.fileManager.setCurrentDirectory(newPath);
    }
}
