package main.java.server;

/**
 * Exception to represent a 500 Internal Server Error
 * 
 * @author Elgar de Groot, October 2013.
 */

public class HTTPInternalServerErrorException extends Exception {

	/**
	 * ID possibly used for serialization.
	 */
	private static final long serialVersionUID = 3736128903332776608L;

	/**
     * Constructor for a new Internal Server Error.
     * @param message Explanation of what went wrong.
     */
    public HTTPInternalServerErrorException(String message) {
        super(message);
    }

    /**
     * Constructor for a new Internal Server Error.
     * @param message Explanation of what went wrong.
     * @param cause Thrown earlier and propagated here.
     */
    public HTTPInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

}
