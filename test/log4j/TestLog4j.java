package log4j;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.snpm.test.TestBase;

public class TestLog4j {
	private final Logger logger = TestBase.logger;
	
	@Test
	public void log4jTest() {
		logger.info("------ Info: log4j ------");
		logger.debug("----- debug: log4j ------");
		logger.error("------ Error: log4j -----");
	}
}