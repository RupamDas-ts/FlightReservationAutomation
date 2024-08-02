package Utility;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static Utility.Constants.test_driver;
import static Utility.Constants.test_session_id;

public class Hooks {
  org.apache.logging.log4j.Logger logger = LogManager.getLogger(Hooks.class);

  @After
  public void closeDriverIfNotClosed(Scenario scenario) {
    if (test_driver.get() != null) {
      final byte[] screenshot = ((TakesScreenshot)test_driver.get()).getScreenshotAs(OutputType.BYTES);
      scenario.attach(screenshot, "image/png", scenario.getName()+"_"+test_session_id);
      try {
        test_driver.get().quit();
      } catch (Exception e) {
        logger.warn("Driver Already quit");
      }
    }
  }
}
