package test.java.parser;

import static org.junit.Assert.*;

import main.java.parser.ServletString;

import org.junit.Before;
import org.junit.Test;

public class ServletStringTest {

	ServletString ss;
	
	@Before
	public void init(){
		ss = new ServletString();
	}
	
	@Test
	public void testAppendHTML1(){
		String html = "test";
		ss.appendHTML(html);
		String actual = ss.code();
		String expected = "out.print(\"test\");\n";
		
		assertEquals("Testing good weather for appendHTML",
				expected, actual);
	}
	
	@Test
	public void testAppendHTML2(){
		String html = "test\n";
		ss.appendHTML(html);
		String actual = ss.code();
		String expected = "out.print(\"test\");\nout.println();\nout.print(\"\");\n";
		
		assertEquals("Testing with a newline in the html code",
				expected, actual);
	}
	
	@Test
	public void testAppendHTML3(){
		String html = "test\r\n";
		ss.appendHTML(html);
		String actual = ss.code();
		String expected = "out.print(\"test\");\nout.println();\nout.print(\"\");\n";
		
		assertEquals("Testing with a carriage return/newline in the html code",
				expected, actual);
	}

	@Test
	public void testAppendHTML4(){
		String html = "test\r";
		ss.appendHTML(html);
		String actual = ss.code();
		String expected = "out.print(\"test\r\");\n";
		
		assertEquals("Testing with just a carriage return in the html code",
				expected, actual);
	}
	
	@Test
	public void testAppendJpDeclaration(){
		String dec = "test";
		ss.appendJspDeclaration(dec);
		String actual = ss.declarations();
		String expected = "test\n";
		
		assertEquals("Testing appendJspDeclaration",
				expected, actual);
	}
	
	@Test
	public void testAppendJspExpression1(){
		String ex = "test";
		ss.appendJspExpression(ex);
		String actual = ss.code();
		String expected = "out.print(\"\" + test);\n";
		
		assertEquals("Testing appendJspExpression good weather",
				expected, actual);
	}
	
	@Test
	public void testAppendJspExpression2(){
		String ex = "test\n";
		ss.appendJspExpression(ex);
		String actual = ss.code();
		String expected = "out.print(\"\" + test );\n";
		
		assertEquals("Testing appendJspDeclaration with a newline",
				expected, actual);
	}

	@Test
	public void testAppendJspExpression3(){
		String ex = "test\r\n";
		ss.appendJspExpression(ex);
		String actual = ss.code();
		String expected = "out.print(\"\" + test );\n";
		
		assertEquals("Testing appendJspDeclaration with a carriage return/newline",
				expected, actual);
	}

	@Test
	public void testAppendJspExpression4(){
		String ex = "test\ntest";
		ss.appendJspExpression(ex);
		String actual = ss.code();
		String expected = "out.print(\"\" + test test);\n";
		
		assertEquals("Testing appendJspDeclaration with a newline and directly afterwards more code",
				expected, actual);
	}
	
	@Test
	public void testAppendJspScriptlet(){
		String sc = "test";
		ss.appendJspScriptlet(sc);
		String actual = ss.code();
		String expected = "test\n";
		
		assertEquals("Testing appendJspScriptlet good weather",
				expected, actual);
	}
	
	@Test
	public void testClassName(){
		String actual = ss.className();
		String expected = "Servlet" + Thread.currentThread().getId();
		
		assertEquals("Testing className()",
				expected, actual);
	}
	
	@Test
	public void testToString(){
		String expected = 
				"import main.java.parser.*;\n"+
				"\n"+
				"public class "+ss.className()+" {\n"+
				"public "+ss.className()+"(){}\n"+
				
					"declaration\n" +
					
				"	public HTMLObject run(){\n"+
				"		HTMLObject out = new HTMLObject();\n"+
		
						"code\n" +
				
				"		return out;\n"+
				"	}\n"+
				"}";
		ss.appendJspDeclaration("declaration");
		ss.appendJspScriptlet("code");
		String actual = ss.toString();
		
		assertEquals("Testing toString good weather",
				expected, actual);
	}
}
