package main.java.parser;


/**
 * Class to store a Servlet class in a String. This makes it easy for the parser to append lines of code to the
 * Servlet class and for the compiler to retrieve the the code for the class.
 * 
 * @author Elgar de Groot, October 2013.
 */

public class ServletString{
	
	private String declarations;
	private String code;
	
	public ServletString(){
		this.declarations = "";
		this.code = "";
	}
	
	public void appendHTML(String html){
		
		String format = html.replaceAll("(\r){0,1}\n", "\");\nout.println();\nout.print(\"");
		
		code += "out.print(\""+format+"\");\n";
	}
	
	public void appendJspDeclaration(String dec){
		declarations += dec + "\n";
	}
	
	public void appendJspExpression(String exp){
		code += "out.print(\"\" + " + exp.replaceAll("(\r){0,1}\n"," ") + ");\n";
	}
	
	public void appendJspScriptlet(String script){
		code += script + "\n";
	}
	
	public String className(){
		return "Servlet"+Thread.currentThread().getId();
	}
	
	public String code(){
		return code;
	}
	
	public String declarations(){
		return declarations;
	}
	
	public String toString(){
		return
				"import main.java.parser.*;\n"+
				"\n"+
				"public class "+className()+" {\n"+
				"public "+className()+"(){}\n"+
				
					declarations +
					
				"	public HTMLObject run(){\n"+
				"		HTMLObject out = new HTMLObject();\n"+
		
						code +
				
				"		return out;\n"+
				"	}\n"+
				"}";
	}
}
