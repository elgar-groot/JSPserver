package main.java.parser;

import java.io.IOException;

/**
 * Exception to represent a failure to parse a Jsp page
 * 
 * @author Elgar de Groot, October 2013.
 */

public class JspParsingException extends IOException {
	
	/**
	 * ID possibly used for serialization.
	 */
	private static final long serialVersionUID = 2875182372685794515L;

	/**
     * Constructor for a new parser exception.
     * @param message Explanation of what went wrong.
     */
    public JspParsingException(String message) {
        super(message);
    }

    /**
     * Constructor for a new parser exception.
     * @param message Explanation of what went wrong.
     * @param cause Thrown earlier and propagated here.
     */
    public JspParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}