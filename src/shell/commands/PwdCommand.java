package shell.commands;

import shell.AbstractCommand;
import shell.ShellState;
import shell.ShellUtils;

public class PwdCommand extends AbstractCommand<ShellState> {
    public PwdCommand() {
        super("pwd", 0);
    }

    public void execute(String argumentsLine, ShellState shellState) {
        String[] arguments = ShellUtils.parseCommandArgsLine(argumentsLine);
        ShellUtils.checkArgumentsNumber(this, arguments.length);

        String workingDir = shellState.fileManager.getCurrentDirectory();
        System.out.println(workingDir);
    }
}
