package test.java.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import main.java.server.HTTPInternalServerErrorException;
import main.java.server.HTTPNotFoundException;
import main.java.server.Server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ServerTest {

	public static File emptyfile;
	public static File invalidfile;
	public static String path = System.getProperty("user.dir") + "/files/pages/";
	public String emptyFile = "junit_emptyfile.jsp";
	public String nonexistantFile = "junit_nonexistantFile.jsp";
	public String invalidFile = "junit_invalidFile.jsp";
	
	private static Server server;
	
	@BeforeClass
	public static void init()throws IOException{
		
		try {
			server = new Server();
		} catch (IOException e) {
			fail("Exception during initializing Server object");
		}
		
		emptyfile = new File(path + "junit_emptyfile.jsp");
		
		if(!emptyfile.exists()){
			emptyfile.createNewFile();
		}
		
		invalidfile = new File(path + "junit_invalidFile.jsp");
		
		if(!invalidfile.exists()){
			invalidfile.createNewFile();
		}
		
		Files.write(invalidfile.toPath(), Arrays.asList("<%= invalid code %>"), StandardCharsets.UTF_8);
		
		File nonexistantFile = new File(path + "junit_nonexistantFile.jsp");
		// making sure the invalid test file doesn't exist
		if(nonexistantFile.exists()){
			nonexistantFile.delete();
		}
	}
	
	@AfterClass
	public static void close(){
		if(emptyfile.exists()){
			emptyfile.delete();
		}
		if(invalidfile.exists()){
			invalidfile.delete();
		}
	}
	
	
	@Test
	public void parseTest1(){
		try {
			String expected = "";
			String actual = server.parse(emptyFile);
			assertEquals("Parsing an empty existing file should return an empty String",
					expected, actual);
		} catch (HTTPInternalServerErrorException | HTTPNotFoundException e) {
			fail("Should parse fine");
		}
	}
	
	@Test
	public void parseTest2(){
		try {
			server.parse(nonexistantFile);
			fail("Should throw an HTTPNotFoundException");
		} catch (HTTPInternalServerErrorException e) {
			fail("Should throw an HTTPNotFoundException");
		} catch (HTTPNotFoundException e) {
			// expected behavior
		}
	}
	
	@Test
	public void parseTest3(){
		try {
			server.parse(invalidFile);
			fail("Should throw an HTTPInternalServerErrorException");
		} catch (HTTPInternalServerErrorException e) {
			//expected behavior
		} catch (HTTPNotFoundException e) {
			fail("Should throw an HTTPInternalServerErrorException");
		}
	}
	
	@Test
	public void getTest1(){
		String expected = 	"HTTP/1.0 200 OK\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n";

		String actual = server.get(emptyFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}
	
	@Test
	public void getTest2(){
		String expected = 	"HTTP/1.0 404 Not Found\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n" +
							"404 Page Not Found";

		String actual = server.get(nonexistantFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}

	@Test
	public void getTest3(){
		String expected = 	"HTTP/1.0 500 Internal Server Error\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n" +
							"500 Internal Server Error";

		String actual = server.get(invalidFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}
	
	@Test
	public void headTest1(){
		String expected = 	"HTTP/1.0 200 OK\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n";

		String actual = server.head(emptyFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}
	
	@Test
	public void headTest2(){
		String expected = 	"HTTP/1.0 404 Not Found\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n";

		String actual = server.head(nonexistantFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}

	@Test
	public void headTest3(){
		String expected = 	"HTTP/1.0 500 Internal Server Error\n"+
							"Connection: close\n"+
							"Server: JSPserver\n"+
							"Content-Type: text/html\n\n";

		String actual = server.head(invalidFile);
		assertEquals("Parsing an empty existing file should return an empty String",
				expected, actual);
	}
	
	@Test
	public void handleRequestTest(){
		
	}
	
	
	
}
