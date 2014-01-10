package test.java.parser;

import static org.junit.Assert.*;

import main.java.parser.JspParsingException;

import org.junit.Test;

public class JspParsingExceptionTest {

	public void throwing() throws JspParsingException{
		throw new JspParsingException("test");
	}
	
	public void rethrow() throws JspParsingException{
		try{
			throw new Exception("test");
		}catch(Exception e){
			throw new JspParsingException("test",e);
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
