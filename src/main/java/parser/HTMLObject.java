package main.java.parser;

/**
 * Class to represent a HTML page in memory.
 * 
 * @author Elgar de Groot, October 2013.
 */

public class HTMLObject {
	
	/**
	 * A String to store the HTML code.
	 */
	private String code;
	
	/**
	 * Constructor to make a new HTMLObject.
	 */
	public HTMLObject(){
		code = "";
	}
	
	/**
	 * Print method to append some HTML code to this page.
	 * @param html The code to append
	 */
	public void print(String html){
		code += html;
	}
	
	/**
	 * PrintLine method to append some HTML code to this page.
	 * @param html The code to append
	 */
	public void println(String html){
		code += html + "\n";
	}
	
	/**
	 * PrintLine method overload to just append a newline.
	 */
	public void println(){
		code += "\n";
	}
	
	/**
	 * Returns the HTML code.
	 */
	public String toString(){
		return code;
	}
}
