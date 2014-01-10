package main.java.server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Simple logger class to log server messages.
 * @author Elgar de Groot, October 2013.
 *
 */
public class ServerLogger {
	
	private Logger logger;
	
	static final String LOG_FILE_PATH = System.getProperty("user.dir") + "/files/log/serverlog.txt";
    
	public ServerLogger() throws IOException {
		
		logger = Logger.getLogger("ServerLog");
		
		FileHandler fh = new FileHandler(LOG_FILE_PATH, true);
		fh.setFormatter(new SimpleFormatter());
	    logger.addHandler(fh);
	    
	}
	
    public void log(String message){
    	logger.log(Level.INFO,message);
    }
    
    public void exception(String ex){
    	logger.log(Level.WARNING,ex);
    }
    
    public void error(String err){
    	logger.log(Level.SEVERE, err);
    }
}
