package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/main/java", glue = { "FeatureFiles", "TestRunner", "StepDefinations" }, plugin = {
  "pretty", "html:target/cucumber-reports" }, tags = "@flightBooking") @Test public class Runner
  extends AbstractTestNGCucumberTests {
  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}