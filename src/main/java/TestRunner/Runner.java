package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = "src/main/java",
                  monochrome = true,
                  glue = { "FeatureFiles", "TestRunner", "StepDefinations", "Utility" },
                  plugin = { "json:target/cucumber-reports/CucumberTestReport.json", "pretty", "html:target/cucumber-reports/testReport.html", "rerun:rerun/failed_scenarios.txt" },
                  tags = "@flightBooking")
public class Runner
  extends AbstractTestNGCucumberTests {
  public Runner() {
    super();
    System.setProperty("log4j.configurationFile", "src/main/java/Utility/log4j2.yaml");
  }

  @Override
  @DataProvider(parallel = true)
  public Object[][] scenarios() {

    // For running same test multiple times
    if (System.getProperty("RUN_N_TIMES") != null) {
      // Based on the value of 'RUN_N_TIMES' that we pass from ENV variable, the scenarios will be duplicated
      int runNTimes = Integer.parseInt(System.getProperty("RUN_N_TIMES"));
      Object[][] newScenarios = new Object[runNTimes][];
      Object cucumberPickle = super.scenarios()[0][0];
      Object featureWrapperImpl = super.scenarios()[0][1];
      for (int i = 0; i < runNTimes; i++) {
        newScenarios[i] = new Object[]{cucumberPickle, featureWrapperImpl};
      }
      return newScenarios;
    }
    return super.scenarios();
  }
}