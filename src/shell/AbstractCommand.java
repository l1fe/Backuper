package shell;

import shell.exceptions.UserInterruptionException;

/**
 * Created by inaumov on 15.07.2014.
 */

public abstract class AbstractCommand<State> implements Command<State> {
    private final String name;
    private final int argsNumber;

    public AbstractCommand(String name, int argsNumber) {
        this.name = name;
        this.argsNumber = argsNumber;
    }

    public String getName() {
        return name;
    }

    public int getArgumentsNumber() {
        return argsNumber;
    }

    public abstract void execute(String argumentsLine, State state) throws UserInterruptionException;
}
