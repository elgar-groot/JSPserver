package main.java.compiler;

import java.io.IOException;
import java.security.SecureClassLoader;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

/**
 * FileManager Class to be used by the compiler.
 * 
 * @author Elgar de Groot, October 2013.
 * @reference http://www.javablogging.com/dynamic-in-memory-compilation/
 */

public class ServletFileManager extends ForwardingJavaFileManager {
	
	private ServletClassObject servletClassObject;

	
	public ServletFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    /**
    * Will be used by us to get the class loader for our
    * compiled class. It creates an anonymous class
    * extending the SecureClassLoader which uses the
    * byte code created by the compiler and stored in
    * the ServletClassObject, and returns the Class for it
    */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name)
                throws ClassNotFoundException {
                byte[] b = servletClassObject.getBytes();
                return super.defineClass(name, servletClassObject
                    .getBytes(), 0, b.length);
            }
        };
    }

    /**
    * Gives the compiler an instance of the JavaClassObject
    * so that the compiler can write the byte code into it.
    */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
        String className, Kind kind, FileObject sibling)
            throws IOException {
    	servletClassObject = new ServletClassObject(className, kind);
        return servletClassObject;
    }
}
