package shell.exceptions;

/**
 * Created by inaumov on 15.07.2014.
 */

public class UserInterruptionException extends Exception {
    public UserInterruptionException() {
        super();
    }

    public UserInterruptionException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
