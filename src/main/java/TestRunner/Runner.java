package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(features = "src/main/java", glue = { "FeatureFiles", "TestRunner" }, plugin = { "pretty",
  "html:target/cucumber-reports" }, tags = "@flightBooking") @Test public class Runner
  extends AbstractTestNGCucumberTests {
}