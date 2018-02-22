package logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
	
	//Intentar implementarlo para crear un log diario
	static Logger logger = Logger.getLogger("foodchain");
	static FileHandler fh;
	
	public static void write(Level logLevel, String message) {
		try {
			fh = new FileHandler("./logs/foodchain.log");
			fh.setFormatter(new SimpleFormatter());
			logger.log(logLevel, message);
		} catch (SecurityException e) {
			//Intentar hacer algo más elegante
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
