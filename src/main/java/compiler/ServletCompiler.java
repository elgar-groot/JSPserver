package main.java.compiler;

import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * Class that compiles java servlet code in memory.
 * 
 * @author Elgar de Groot, October 2013.
 */

public class ServletCompiler {
	
	private JavaCompiler compiler;
	private JavaFileManager fileManager;
	private ServletFileObject code;
	
	public ServletCompiler(ServletFileObject code){
		this.compiler = ToolProvider.getSystemJavaCompiler();
		this.fileManager = new ServletFileManager(compiler.getStandardFileManager(null, null, null));
		this.code = code;
	}
	
	public void compile() throws CompilationException{
		try{
			
			Iterable<? extends JavaFileObject> files = Arrays.asList(code);
			
			boolean compiled = compiler.getTask(null, fileManager, null, null, null, files).call();
			
			if(!compiled){
				throw new Exception();
			}
		}catch (Exception e){
			throw new CompilationException("Exception during compilation",e);
		}
	}
	
	public Object loadServlet()
			throws LoadCompiledServletException{
		Object servlet = null;
		try {
			servlet = fileManager.getClassLoader(null).loadClass(code.className()).newInstance();
		} catch (Exception e) {
			throw new LoadCompiledServletException("Unable to load servlet from memory",e);
		}
		return servlet;
	}
	
}
