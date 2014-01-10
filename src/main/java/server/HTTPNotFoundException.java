package main.java.server;
/**
 * Exception to represent a 404 Not Found exception
 * 
 * @author Elgar de Groot, October 2013.
 */

public class HTTPNotFoundException extends Exception {

	/**
	 * ID possibly used for serialization.
	 */
	private static final long serialVersionUID = -5295369672291176362L;

	/**
     * Constructor for a new 404 Not Found exception.
     * @param message Explanation of what went wrong.
     */
    public HTTPNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor for a new 404 Not Found exception.
     * @param message Explanation of what went wrong.
     * @param cause Thrown earlier and propagated here.
     */
    public HTTPNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
