package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = { "@rerun/failed_scenarios.txt" },
                  monochrome = true,
                  glue = { "FeatureFiles", "TestRunner", "StepDefinations", "Utility" },
                  plugin = { "json:target/cucumber-reports/CucumberTestReportFailedTestCases.json","pretty", "html:target/cucumber-reports/testReportFailedTestCases.html", "rerun:rerun/failed_rerun_scenarios.txt" })
public class FailedTestRunner extends AbstractTestNGCucumberTests {
  public FailedTestRunner() {
    super();
    System.setProperty("log4j.configurationFile", "src/main/java/Utility/log4j2.yaml");
  }

  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {
    return super.scenarios();
  }
}