package shell;

public class ShellUtils {
    public static String[] parseCommandArgsLine(String commandArgsLine) {
        if (commandArgsLine == null || commandArgsLine.trim().isEmpty()) {
            return new String[0];
        }

        commandArgsLine = commandArgsLine.trim();
        String[] args = commandArgsLine.split("\\s+");

        for (int i = 0; i < args.length; ++i) {
            args[i] = args[i].trim();
        }

        return args;
    }

    public static void checkArgumentsNumber(Command command, int argsNumber) {
        if (command.getArgumentsNumber() == -1) {
            return;
        }

        if (command.getArgumentsNumber() != argsNumber) {
            throw new IllegalArgumentException("Command " + command.getName() + ":" +
                                                "expected " + command.getArgumentsNumber() + "arguments");
        }
    }
}