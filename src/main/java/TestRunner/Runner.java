package TestRunner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.java.AfterAll;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

import static Utility.Constants.userName;

@CucumberOptions(features = "src/main/java",
                  monochrome = true,
                  glue = { "FeatureFiles", "TestRunner", "StepDefinations", "Utility" },
                  plugin = { "json:target/cucumber-reports/CucumberTestReport.json", "pretty", "html:target/cucumber-reports/testReport.html", "rerun:rerun/failed_scenarios.txt", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
                  tags = "@flightBooking")
public class Runner
  extends AbstractTestNGCucumberTests {
  public Runner() {
    super();
    System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.yaml");
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

  @AfterAll
  public static void setParamsForReport(){
    ExtentReports extent = ExtentService.getInstance();
    extent.setSystemInfo("Email","dasrupam740@gmail.com");
    extent.setSystemInfo("User", userName.get());
    extent.setSystemInfo("Environment", "QA");
  }
}