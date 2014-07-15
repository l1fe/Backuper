package shell;

import javafx.util.Pair;
import shell.exceptions.UserInterruptionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Shell<State> {
    private static final String INVITATION_PROMPT = " $ ";

    private final Map<String, Command> commandsMap = new HashMap<String, Command>();
    private State state = null;

    private Pair<String, String> parseInputLine(String inputLine) {
        if (inputLine == null || inputLine.trim().isEmpty()) {
            throw new IllegalArgumentException("Null or empty input line");
        }

        inputLine = inputLine.trim();

        String commandName = null;
        String commandArgs = null;

        int spaceFirstEntryIndex = inputLine.indexOf(' ');
        if (spaceFirstEntryIndex == -1) {
            commandName = inputLine;
        } else {
            commandName = inputLine.substring(0, spaceFirstEntryIndex).trim();
            commandArgs = inputLine.substring(spaceFirstEntryIndex).trim();
        }

        return new Pair<String, String>(commandName, commandArgs);
    }

    private void interactiveMode() {
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(INVITATION_PROMPT);
            Pair<String, String> nextEntry = null;

            try {
                nextEntry = parseInputLine(inputStreamReader.readLine());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                continue;
            } catch (IOException e) {
                System.exit(1);
            }

            String commandName = nextEntry.getKey();
            String commandArgs = nextEntry.getValue();

            Command command = getCommand(commandName);

            if (command == null) {
                System.err.println("Command " + commandName + " not found");
                continue;
            }

            try {
                command.execute(commandArgs, state);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            } catch (UserInterruptionException e) {
                System.exit(0);
            }
        }
    }

    public Command getCommand(String commandName) {
        if (commandName == null || commandName.trim().isEmpty()) {
            throw new IllegalArgumentException("Null or empty command name");
        }

        return commandsMap.get(commandName);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addCommand(Command command) {
        if (command == null) {
            throw new IllegalArgumentException("Null command");
        }

        commandsMap.put(command.getName(), command);
    }

    public void run() {
        interactiveMode();
    }
}
