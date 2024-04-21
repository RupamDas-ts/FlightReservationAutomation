package Utility;

import io.cucumber.java.After;
import org.apache.logging.log4j.LogManager;

import static Utility.Constants.test_driver;

public class Hooks {
  org.apache.logging.log4j.Logger logger = LogManager.getLogger(Hooks.class);

  @After
  public void closeDriverIfNotClosed() {
    if (test_driver.get() != null) {
      try {
        test_driver.get().quit();
      } catch (Exception e) {
        logger.warn("Driver Already quit");
      }
    }
  }
}
