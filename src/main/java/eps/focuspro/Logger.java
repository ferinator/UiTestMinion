package eps.focuspro;

import java.util.Date;


public class Logger {

    public static void logError(Throwable cause) {
        logError("", cause);
    }

    public static void logError(String message, Throwable cause) {
        System.out.println();
        System.out.println(getDateForLog() + "Error: " + message);
        if (cause != null){
            System.out.println("| Message: " + cause.getMessage() + " - Cause: " + cause.getCause() + " - " + cause.getClass());
            System.out.println("| Stack Trace:");
            for (StackTraceElement trace : cause.getStackTrace()) {
                System.out.println("|   at " + trace);
            }
        } else {
            System.out.println("| Cause: null");
        }
    }

    public static String getDateForLog(){
        Date date = new Date();
        return date.toString() + " EPS Plugins - Page/Space Tree Creator: ";
    }
}
