package shell.commands;


import shell.AbstractCommand;
import shell.ShellUtils;
import shell.exceptions.UserInterruptionException;

public class ExitCommand<State> extends AbstractCommand<State> {
    public ExitCommand() {
        super("exit", 0);
    }

    public void execute(String argumentsLine, State state) throws UserInterruptionException {
        String[] args = ShellUtils.parseCommandArgsLine(argumentsLine);
        ShellUtils.checkArgumentsNumber(this, args.length);

        throw new UserInterruptionException();
    }
}
