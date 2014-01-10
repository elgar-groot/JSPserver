package test.java.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import main.java.parser.JspParser;
import main.java.parser.ServletString;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JspParserTest {
	
	public static File testfile;
	public static String path = System.getProperty("user.dir") + "/files/pages/";
	public String validFile = "junit_parsertest.jsp";
	public String invalidFile = "junit_invalidfile.jsp";
	
	@BeforeClass
	public static void init()throws IOException{
		
		testfile = new File(path + "junit_parsertest.jsp");
		
		if(!testfile.exists()){
			testfile.createNewFile();
		}
		
		File invalidFile = new File(path + "junit_invalidfile.jsp");
		// making sure the invalid test file doesn't exist
		if(invalidFile.exists()){
			invalidFile.delete();
		}
	}
	
	@AfterClass
	public static void close(){
		if(testfile.exists()){
			testfile.delete();
		}
	}
	
	@Test
	public void constructorTest1() throws FileNotFoundException{
		try{
			new JspParser(validFile);
		}catch(Exception e) {
			fail("Creating parser on valid file shouldn't throw an exception");
		}
	}

	@Test
	public void constructorTest2() throws FileNotFoundException{
		try{
			new JspParser(invalidFile);
			fail("Creating parser on an invalid file should throw an exception");
		}catch(Exception e) {
			// expected behavior
		}
	}

	@Test
	public void constructorTest3() throws FileNotFoundException{
		String nojspfile = "test.txt";
		try{
			new JspParser(nojspfile);
			fail("Creating parser on a file with a different extension than '.jsp' should throw an exception");
		}catch(Exception e) {
			// expected behavior
		}
	}
	
	@Test
	public void getPathTest(){
		try{
			JspParser p = new JspParser(validFile);
			String expected = path + validFile;
			String actual = p.getPath();
			assertEquals("Testing the getPath() method",
					expected,actual);
			
		}catch(Exception e) {
			fail("Creating parser on valid file shouldn't throw an exception");
		}
	}
	
	@Test
	public void readFileToStringTest(){
		try{
			JspParser p = new JspParser(validFile);
			String expected = "";
			String actual = p.readFileToString();
			assertEquals("Testing the readFileToString() method",
					expected,actual);
			
		}catch(Exception e) {
			fail("Reading an existing file should not throw an exeption");
		}
	}
	
	@Test
	public void parseTest(){
		try{
			JspParser p = new JspParser(validFile);
			String expectedCode = "out.print(\"\");\n";
			String expectedDecl = "";
			ServletString res = p.parse();
			String actualCode = res.code();
			String actualDecl = res.declarations();
			assertEquals("The code of the resulting ServletString should be a statement to print an empty String",
					expectedCode, actualCode);
			assertEquals("The declarations of the resulting ServletString should be an empty String",
					expectedDecl, actualDecl);
			
		}catch(Exception e) {
			fail("Parsing an existing empty file should not throw an exeption");
		}
	}
	
	@Test
	public void parseTagsTest1(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "HTML";
			p.parseTags(serv,file);
			
			String expected = "out.print(\"HTML\");\n";
			String actual 	= serv.code();
			
			assertEquals("Shouldn't match and only add the file as html to the ServletString", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}
	
	@Test
	public void parseTagsTest2(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "<%!decl%>";
			p.parseTags(serv,file);
			
			String expected = "decl\n";
			String actual 	= serv.declarations();
			
			assertEquals("Should match and add as declaration", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}

	@Test
	public void parseTagsTest3(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "<%=exp%>";
			p.parseTags(serv,file);
			
			String expected = 	"out.print(\"\");\n" +
								"out.print(\"\" + exp);\n" +
								"out.print(\"\");\n";
			String actual 	= serv.code();
			
			assertEquals("Should match and add as expression", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}

	@Test
	public void parseTagsTest4(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "<%-- comment --%>";
			p.parseTags(serv,file);
			
			String expected = 	"out.print(\"\");\n" +
								"out.print(\"\");\n";
			String actual 	= serv.code();
			
			assertEquals("Should match but nothing added to the ServletString", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}

	@Test
	public void parseTagsTest5(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "<% scriptlet%>";
			p.parseTags(serv,file);
			
			String expected = 	"out.print(\"\");\n" +
								" scriptlet\n" +
								"out.print(\"\");\n";
			String actual 	= serv.code();
			
			assertEquals("Should match and added as scriptlet", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}

	@Test
	public void parseTagsTest6(){
		try{
			JspParser p = new JspParser(validFile);
			ServletString serv = new ServletString();
			String file = "<%scriptlet%>";
			p.parseTags(serv,file);

			String expected = 	"out.print(\"\");\n" +
								"scriptlet\n" +
								"out.print(\"\");\n";
			String actual 	= serv.code();
			
			assertEquals("Should match and added as scriptlet", 
					expected, actual);
		}catch(Exception e) {
			fail("Unexpected exception thrown in test where it shouldn't happen");
		}
	}
}
