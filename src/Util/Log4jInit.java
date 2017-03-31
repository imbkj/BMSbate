package Util;

import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Log4jInit extends HttpServlet {
	private static Logger logger=LogManager.getLogger(Log4jInit.class.getName());

	public Log4jInit() {
		
	}


	public static void toPrint(String content) {
		System.out.println(content);
	}

	public static void toLog(String content){
		logger.error(content);
	}
}
