package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import test.java.compiler.*;
import test.java.parser.*;
import test.java.server.*;

@RunWith(Suite.class)
@SuiteClasses({ServletCompilerTest.class, HTMLObjectTest.class, 
	JspParserTest.class, ServletStringTest.class, ServerTest.class,
	CompilationExceptionTest.class, LoadCompiledServletExceptionTest.class,
	JspParsingExceptionTest.class,HTTPInternalServerErrorExceptionTest.class,
	HTTPNotFoundExceptionTest.class,ServerLoggerTest.class})
public class AllTests {

}
