package test.java.compiler;

import static org.junit.Assert.*;

import main.java.compiler.LoadCompiledServletException;

import org.junit.Test;

public class LoadCompiledServletExceptionTest {

	public void throwing() throws LoadCompiledServletException{
		throw new LoadCompiledServletException("test");
	}
	
	public void rethrow() throws LoadCompiledServletException{
		try{
			throw new Exception("test");
		}catch(Exception e){
			throw new LoadCompiledServletException("test",e);
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
