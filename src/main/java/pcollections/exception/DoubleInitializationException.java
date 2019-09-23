package pcollections.exception;

public class DoubleInitializationException extends RuntimeException {

    private static final String MESSAGE = "Use getInstance() method to get the single instance of this class";

    public DoubleInitializationException() {
        super(MESSAGE);
    }
}
