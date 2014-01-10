package main.java.server;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import main.java.parser.*;
import main.java.compiler.*;

/**
 * Class that represents the JSP server. Can handle GET and HEAD requests for 
 * JSP files, parse compile and execute them and send HTML back to the client.
 * Is not multi-threaded, although it would be possible to have multiple 
 * servlet classes in memory for multiple JSP files.
 * 
 * @author Elgar de Groot, October 2013.
 * @reference http://fragments.turtlemeat.com/javawebserver.php
 */

public class Server {
	
	/**
	 * Constants for various HTTP codes and the port number
	 */
	static final int PORT = 10001;
	static final int OK = 200;
	static final int BAD_REQUEST = 400;
	static final int FORBIDDEN = 403;
	static final int NOT_FOUND = 404;
	static final int INTERNAL_SERVER_ERROR = 500;
	static final int NOT_IMPLEMENTED = 501;
	
	/**
	 * The socket this server is listening on
	 */
	private ServerSocket socket;
	
	/**
	 * The logger for the server
	 */
	private ServerLogger logger;
	
	/**
	 * Main method to start the server
	 * @param args
	 */
	public static void main(String [] args){
		try {
			Server server = new Server();
			server.listen();
		} catch (IOException e) {
			System.exit(0);
		}
	}
	
	/**
	 * Creates a new Server object and it's logger and socket.
	 * @throws IOException
	 */
	public Server() throws IOException {
		
		logger = new ServerLogger();
		
		logger.log("Binding at localhost:"+PORT+"...");
		
		socket = new ServerSocket(PORT);
		
	}
	
	/**
	 * Start listening to the port for incoming requests.
	 * @throws IOException
	 */
	public void listen() throws IOException{
		
		while(true)
		{   
			logger.log("Waiting for a client to connect...");
			
			Socket clientSocket = socket.accept();
			logger.log("Client connected");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			
			handleRequest(in, out);
			
			out.close();
		}
		
	}
	
	/**
	 * Handles an incoming request from the client. Checks if it is a valid
	 * request, sends the result of the request back or a message if something
	 * went wrong.
	 * @param in BufferedReader that contains the request.
	 * @param out DataOutputStream to send messages back to the client.s
	 */
	public void handleRequest(BufferedReader in, DataOutputStream out){
		
		try {
			// Try to read the incoming request. If something might go wrong, 
			// send a Bad Request response to the client
			String request;
			String fileName;
			try {
				request = in.readLine();
				logger.log("Recieved request: "+request);
				String path = request.split(" ")[1];
				fileName = path.substring(path.lastIndexOf('/')+1, path.length());
			} catch (Exception e) {
				logger.exception("Bad request. Sending 400 Bad Request.");
				out.writeBytes(constructHttpHeader(BAD_REQUEST));
				out.writeBytes("400 Bad Request");
				return; 
			}
			
			// Handle GET request
			if (request.startsWith("GET")){
				out.writeBytes(get(fileName));
				
			} // HEAD does the same except returning the page
			else if (request.startsWith("HEAD")){
				out.writeBytes(head(fileName));
			}
			// Other HTTP request methods are not implemented.
			// Sending a 501 response back
			else {
				logger.log("Recieved unimplemented HTTP request. Sending 501 Not Yet Implemented.");
				out.writeBytes(constructHttpHeader(NOT_IMPLEMENTED));
				out.writeBytes("501 Not Yet Implemented");
			}
			
		}catch (IOException e) {
			// Something went wrong during writing to out
			logger.exception("Exception during writing to out, unable to handle request.");
		}
	}
	
	/**
	 * This function parses a jsp file to java code, compiles and executes it
	 * and returns the resulting html page as String.
	 * @param fileName The name of the jsp file requested
	 * @return String The html file as String object
	 * @throws HTTPInternalServerErrorException
	 * @throws HTTPNotFoundException
	 */
	public String parse(String fileName) 
			throws HTTPInternalServerErrorException, HTTPNotFoundException
	{
		try {
			
			JspParser parser = new JspParser(fileName);
			
			ServletString code = parser.parse();
			
			ServletFileObject sfo = new ServletFileObject(code.className(),code.toString());
			
			ServletCompiler compiler = new ServletCompiler(sfo);
			
			compiler.compile();
			
			Object servlet = compiler.loadServlet();
				
			HTMLObject page = (HTMLObject) servlet.getClass().getMethod("run").invoke(servlet);
				
			return page.toString();
			
			
		} catch (IllegalAccessException
				| InvocationTargetException
				| NoSuchMethodException
				| LoadCompiledServletException 
				| JspParsingException 
				| CompilationException e) {
			throw new HTTPInternalServerErrorException("",e);
		} catch (FileNotFoundException e) {
			throw new HTTPNotFoundException("",e);
		}
	}
	
	/**
	 * Handles a GET request for a file.
	 * @param fileName Name of the requested file
	 * @return String output to be send back to the client.
	 */
	public String get(String fileName){
		String output = "";
		try {
			String file = parse(fileName);
			output = constructHttpHeader(OK) + file;
		} catch (HTTPInternalServerErrorException e) {
			logger.exception("Exception raised during parsing of file. Sending 500 Internal Server Error.");
			output = constructHttpHeader(INTERNAL_SERVER_ERROR) + "500 Internal Server Error";
		} catch (HTTPNotFoundException e) {
			logger.exception("File not found. Sending 404 Page Not Found.");
			output = constructHttpHeader(NOT_FOUND) + "404 Page Not Found";
		}
		return output;
	}
	
	/**
	 * Handles a HEAD request. Returns precisely the header that a GET request would
	 * send back.
	 * @param fileName Name of the requested file
	 * @return String output to be send back to the client
	 */
	public String head(String fileName){
		String output = "";
		try {
			parse(fileName);
			output = constructHttpHeader(OK);
		} catch (HTTPInternalServerErrorException e) {
			logger.exception("Exception raised during parsing of file. Sending 500 Internal Server Error.");
			output = constructHttpHeader(INTERNAL_SERVER_ERROR);
		} catch (HTTPNotFoundException e) {
			logger.exception("File not found. Sending 404 Page Not Found.");
			output = constructHttpHeader(NOT_FOUND);
		}
		return output;
	}
	
	/**
	 * Generates a new HTTP header with the given return code.
	 * @param return_code 
	 * @return String
	 */
	private String constructHttpHeader(int returnCode){
		String header = "HTTP/1.0 ";
		
	    switch (returnCode) {
	      case OK:
	        header += "200 OK";
	        break;
	      case BAD_REQUEST:
	        header += "400 Bad Request";
	        break;
	      case FORBIDDEN:
	        header += "403 Forbidden";
	        break;
	      case NOT_FOUND:
	        header += "404 Not Found";
	        break;
	      case INTERNAL_SERVER_ERROR:
	        header += "500 Internal Server Error";
	        break;
	      case NOT_IMPLEMENTED:
	        header += "501 Not Implemented";
	        break;
	    }

	    header += "\n"; 
	    header += "Connection: close\n"; 
	    header += "Server: JSPserver\n"; 
	    header += "Content-Type: text/html\n";
	    header += "\n";
	    return header;
	}
}
