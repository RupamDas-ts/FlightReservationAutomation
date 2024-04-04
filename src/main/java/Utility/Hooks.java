package Utility;

import io.cucumber.java.After;

import static Utility.Constants.test_driver;

public class Hooks {
  @After
  public void closeDriverIfNotClosed() {
    if (test_driver.get() != null) {
      try {
        test_driver.get().quit();
      } catch (Exception e) {
        System.out.println("Driver Already quit");
      }
    }
  }
}
