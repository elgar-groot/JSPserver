package main.java.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * Class that represents a .class file (or: java byte code) in memory.
 * 
 * @author Elgar de Groot, October 2013.
 * @reference http://www.javablogging.com/dynamic-in-memory-compilation/
 */
public class ServletClassObject extends SimpleJavaFileObject {
		
	    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

	    
	    public ServletClassObject(String name, Kind kind) {
	    	
	        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
	    }

	    
	    public byte[] getBytes() {
	        return bos.toByteArray();
	    }

	    
	    @Override
	    public OutputStream openOutputStream() throws IOException {
	        return bos;
	    }
}
