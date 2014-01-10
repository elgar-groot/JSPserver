package test.java.server;

import static org.junit.Assert.*;


import main.java.server.HTTPInternalServerErrorException;

import org.junit.Test;

public class HTTPInternalServerErrorExceptionTest {

	public void throwing() throws HTTPInternalServerErrorException{
		throw new HTTPInternalServerErrorException("test");
	}
	
	public void rethrow() throws HTTPInternalServerErrorException{
		try{
			throw new Exception("test");
		}catch(Exception e){
			throw new HTTPInternalServerErrorException("test",e);
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
