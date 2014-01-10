package test.java.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.compiler.*;

public class ServletCompilerTest {
	

	private String validSource = 
	"public class Servlet {\n"+
	"	public Servlet(){}\n"+
	"	public String run(){\n"+
	"		return \"OK\";\n"+
	"	}\n"+
	"}";
	private String validClassname = "Servlet";

	private ServletCompiler initCompilerValid(){
		ServletFileObject sfo = new ServletFileObject(validClassname, validSource);
		return new ServletCompiler(sfo);
	}

	private ServletCompiler initCompilerEmptyServletFileObject(){
		return new ServletCompiler(null);
	}
	

	@Test
	public void compileTest1(){
		ServletCompiler c = initCompilerValid();
		try{
			c.compile();
		}catch(CompilationException e){
			fail("Compiling with valid code and classname should not throw an exception");
		}
	}
	
	@Test
	public void compileTest2(){
		ServletCompiler c = initCompilerEmptyServletFileObject();
		try{
			c.compile();
			fail("Compiling with an empty ServletFileObject should throw a CompilationException");
		}catch(CompilationException e){
			String expected = "Exception during compilation";
			String actual = e.getMessage();
			assertEquals("Checking if the CompilationException has the correct message",
					expected, actual);
		}
	}
	
	@Test
	public void loadservletTest1() throws LoadCompiledServletException{
		ServletCompiler c = initCompilerValid();
		try{
			c.compile();
		}catch(CompilationException e){
			fail("Compiling with valid code and classname should not throw an exception");
		}
		Object s = c.loadServlet();
		
		String expected = validClassname;
		String actual = s.getClass().getName();
		
		assertEquals("Compiling with valid code should return a valid Servlet", 
					expected, actual);
	}

	
	@Test(expected = LoadCompiledServletException.class)
	public void loadservletTest2() throws LoadCompiledServletException{
		ServletCompiler c = initCompilerEmptyServletFileObject();
		try{
			c.compile();
		}catch (Exception e){
			c.loadServlet();
			fail("Expected LoadCompiledServletException");
		}
	}
	
	@Test(expected = LoadCompiledServletException.class)
	public void loadservletTest3() throws LoadCompiledServletException{
		ServletCompiler c = initCompilerValid();
		c.loadServlet();
	}
}
