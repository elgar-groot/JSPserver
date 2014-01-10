package main.java.compiler;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Class that represents a .java file in memory.
 * 
 * @author Elgar de Groot, October 2013.
 * @reference http://www.javablogging.com/dynamic-in-memory-compilation/
 */

public class ServletFileObject extends SimpleJavaFileObject {
	
	/**
	 * The source code of this "file".
	 */
	private final String code;
	
	/**
	 * The name of the class this "file" represents.
	 */
	private final String className;
    
    /**
     * Constructs a new ServletFileObject.
     * @param name the name of the compilation unit represented by this file object
     * @param code the source code for the compilation unit represented by this file object
     */
	public ServletFileObject(String name, String code) {
		
		super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
				Kind.SOURCE);
		this.code = code;
		this.className = name;
	}
	
	public String className(){
		return className;
	}
	
	@Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}
