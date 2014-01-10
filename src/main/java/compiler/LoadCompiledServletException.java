package main.java.compiler;

/**
 * Exception to represent a failure to load a compiled Servlet
 * from memory
 * 
 * @author Elgar de Groot, October 2013.
 */

public class LoadCompiledServletException extends Exception {

	/**
	 * ID possibly used for serialization.
	 */
	private static final long serialVersionUID = -7233775281358926985L;
	
	/**
     * Constructor for a new servlet loading exception.
     * @param message Explanation of what went wrong.
     */
    public LoadCompiledServletException(String message) {
        super(message);
    }

    /**
     * Constructor for a new servlet loading exception.
     * @param message Explanation of what went wrong.
     * @param cause Thrown earlier and propagated here.
     */
    public LoadCompiledServletException(String message, Throwable cause) {
        super(message, cause);
    }

}
