package eps.focuspro.error;

import java.util.LinkedList;
import java.util.List;


public class UiException extends RuntimeException {

    public UiException(String message) {
        super(message);
    }

    public UiException(String message, Throwable cause) {
        super(message, cause);
    }
}
