package shell;

import shell.exceptions.UserInterruptionException;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public interface Command<State> {
    String getName();

    int getArgumentsNumber();

    void execute(String argumentsLine, State state) throws UserInterruptionException;
}
