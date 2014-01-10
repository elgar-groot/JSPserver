package test.java.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.server.ServerLogger;

import org.junit.Test;

public class ServerLoggerTest {
	
	
	@Test
	public void test() {
		try {
			new ServerLogger();
			//servlog is now properly initialized
			Logger log = Logger.getLogger("ServerLog");
			// checking if the levels used in ServerLogger are usable
			// and if so we assume the logger behaves as expected
			assertTrue("Logger should be able to log with level INFO",
					log.isLoggable(Level.INFO));
			assertTrue("Logger should be able to log with level WARNING",
					log.isLoggable(Level.WARNING));
			assertTrue("Logger should be able to log with level SEVERE",
					log.isLoggable(Level.SEVERE));
		} catch (IOException e) {
			fail("Unexpected exception when initializing new ServerLogger");
		}
	}

}
