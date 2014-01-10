package test.java.parser;

import static org.junit.Assert.*;

import main.java.parser.HTMLObject;

import org.junit.Before;
import org.junit.Test;

public class HTMLObjectTest {
	
	HTMLObject o;
	
	@Before
	public void init(){
		o = new HTMLObject();
	}
	
	@Test
	public void constructorTest() {
		assertEquals("New HTMLObject should contain empty string as code"
				, "", o.toString());
	}
	
	@Test
	public void testPrint(){
		o.print("test");
		assertEquals("After printing a single line, the HTMLObject should contain only that line"
				, "test", o.toString());
	}
	
	@Test
	public void testPrintln1(){
		o.println("test");
		assertEquals("After printing with a newline, the HTMLObject should end with \\n"
				, "test\n", o.toString());
	}
	
	@Test
	public void testPrintln2(){
		o.println();
		assertEquals("After using only println() , the HTMLObject should only contain \\n"
				, "\n", o.toString());
	}
	
	@Test
	public void testPrintPrintln(){
		HTMLObject o1 = new HTMLObject();
		o1.print("test");
		o1.println();
		HTMLObject o2 = new HTMLObject();
		o2.println("test");
		assertEquals("Using print(line) and then println() should return the same as using println(line)"
				, o1.toString(), o2.toString());
	}
}
