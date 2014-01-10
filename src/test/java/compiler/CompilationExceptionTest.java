package test.java.compiler;

import static org.junit.Assert.*;

import main.java.compiler.CompilationException;

import org.junit.Test;

public class CompilationExceptionTest {

	public void throwing() throws CompilationException{
		throw new CompilationException("test");
	}
	
	public void rethrow() throws CompilationException{
		try{
			throw new Exception("test");
		}catch(Exception e){
			throw new CompilationException("test",e);
		}
	}
	
	@Test
	public void exceptionTest1(){
		try{
			throwing();
			fail("Should have thrown an exception");
		}catch(Exception e){
			String expected = "test";
			String actual = e.getMessage();
			assertEquals("Message should be \"test\"", expected, actual);
		}
	}
	
	@Test
	public void exceptionTest2(){
		try{
			rethrow();
			fail("Should have thrown an exception");
		}catch(Exception e){
			String expected = "test";
			String actual = e.getCause().getMessage();
			assertEquals("Message should be \"test\"", expected, actual);
		}
	}
}
