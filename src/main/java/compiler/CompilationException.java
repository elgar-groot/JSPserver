package main.java.compiler;

/**
 * Exception to represent a failure to load a compiled Servlet
 * from memory
 * 
 * @author Elgar de Groot, October 2013.
 */

public class CompilationException extends Exception {

	/**
	 * ID possibly used for serialization.
	 */
	private static final long serialVersionUID = 8838381212554574915L;

	/**
     * Constructor for a new compilation exception.
     * @param message Explanation of what went wrong.
     */
    public CompilationException(String message) {
        super(message);
    }

    /**
     * Constructor for a new compilation exception.
     * @param message Explanation of what went wrong.
     * @param cause Thrown earlier and propagated here.
     */
    public CompilationException(String message, Throwable cause) {
        super(message, cause);
    }

}