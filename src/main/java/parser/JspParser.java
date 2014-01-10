package main.java.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class that can load a jsp page and parse it, returning a ServletString object.
 * During initializing it checks if the requested file exists.
 * 
 * @author Elgar de Groot, October 2013.
 */

public final class JspParser {
	
	/**
	 * Folder where the JSP pages are stored.
	 */
	private String jspPageFolder = System.getProperty("user.dir") + "/files/pages/";
	
	/**
	 * Regular expression to match a jsp scripting tag in a jsp page.
	 */
	private Pattern jsptag = Pattern.compile("(.*?)<%(.*?)%>(.*+)",Pattern.DOTALL);
	
	/**
	 * The name of the JSP file, without extension.
	 */
	private String fileName;
	
	/**
	 * Creates a new JspParser object if the file that is supposed to be parsed exists.
	 * @param name
	 * @throws FileNotFoundException
	 */
	public JspParser(String name ) throws FileNotFoundException {
		if (validPageName(name) && pageExists(name)) {
			fileName = name;
		}
		else{
			throw new FileNotFoundException("Not a JSP file");
		}
	}
	
	/**
	 * Simple method that checks if the given file has the .jsp extension.
	 * @param name
	 * @return boolean
	 */
	public boolean validPageName(String name){
		return name.matches("(.*)\\.jsp$") ? true : false;
	}
	
	/**
	 * Simple method that checks whether or not the file exists in the current folder.
	 * @param name
	 * @return boolean
	 */
	public boolean pageExists(String name){
		return new File(jspPageFolder+name).exists();
	}
	
	/**
	 * Returns the full path of the file to be parsed.
	 * @return
	 */
	public String getPath(){
		return jspPageFolder + fileName;
	}
	
	/**
	 * Method that reads an entire file with UTF_8 encoding
	 * and converts it to a String.
	 * @return String The file as a String
	 * @throws IOException
	 * @reference http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file?rq=1
	 */
	public String readFileToString() throws IOException{
		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = Files.readAllBytes(Paths.get(getPath()));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	/**
	 * Method wrapping around the parseTags() method 
	 * @return
	 * @throws JspParsingException
	 */
	public ServletString parse() throws JspParsingException{
		try {
			String page = readFileToString();

			ServletString serv = new ServletString();
			
			parseTags(serv, page);
			
			return serv;
			
		} catch (IOException e) {
			throw new JspParsingException("",e);
		}
	}
	
	/**
	 * Method that recursively parses jsp tags from file and adds the code to serv.
	 * @param serv The ServletString object that represents the Servlet class
	 * @param file The jsp page code in String format to be parsed
	 */
	public void parseTags(ServletString serv, String file) {
		// Match the first jsp tag in the file
		Matcher matcher = jsptag.matcher(file);
		if(matcher.find()){
			// Matches all the html code in the file before the jsp tag
			String html = matcher.group(1);
			// Matches everything inside the jsp tags 
			String jspContent = matcher.group(2).substring(1);
			// The first character defines the jsp scripting group
			char jspTagID = matcher.group(2).charAt(0);
			// Matches everything after the jsp tag
			String rest = matcher.group(3);
			
			serv.appendHTML(html);
			
			// Switch between the four possible jsp scripting tags.
			// If the tag is not one of these, it will not be parsed.
			switch (jspTagID) {
			  case '!':
				serv.appendJspDeclaration(jspContent);
				break;
			  case '=':
				serv.appendJspExpression(jspContent);
				break;
			  case '-':
				break;
			  default:
				// Add the jspTagID for when the code starts immediately 
				// after the tag
				serv.appendJspScriptlet(jspTagID + jspContent);
			}
			// Parse the rest of the file recursively
			parseTags(serv,rest);
		}
		else{
			serv.appendHTML(file);
		}
	}
	
}
