package errorhandling;

/**
 *
 * @author Mibse
 */
public class MissingInputException extends Exception {
    
    public MissingInputException(String message){
        super(message);
    }

    public MissingInputException() {
        super("Requested item could not be found");
    }
}
