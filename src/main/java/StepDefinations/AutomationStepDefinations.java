package StepDefinations;

import Utility.AutomationHelper;
import Utility.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.testng.asserts.SoftAssert;

public class AutomationStepDefinations {
  org.apache.logging.log4j.Logger logger = LogManager.getLogger(AutomationStepDefinations.class);
  AutomationHelper automationHelper = new AutomationHelper();

  @Given("^Initialize soft assert$")
  public void initializeSoftAsset() {
    SoftAssert initSoftAssert = new SoftAssert();
    Constants.SOFT_ASSERT.set(initSoftAssert);
  }

  @And("^Setup User Details for new Account creation$")
  public void setupAccountDetails() {
    String emails = automationHelper.getRandomEmailID();
    if (emails.isEmpty())
      logger.error("Unable to generate Random Email.");
  }

  @Then("^Create WedDriver with given ([a-zA-Z0-9_=,:.+\\-]+)$")
  public void createWedDriverWithGivenCapabilities(String caps) {
    automationHelper.getWebDriverUsingGivenCaps(caps);
  }

  @Then("^Open MMT Log in page$")
  public void openMMTLogInPage() {
    automationHelper.openMMTSignUpPage();
  }

  @And("^Create new Account$")
  public void createNewAccount() {
    automationHelper.createNewMMTAccount();
  }

  @Then("^Book Flight From ([a-zA-Z0-9_=,:.+\\- ]+) to ([a-zA-Z0-9_=,:.+\\- ]+) for given ([a-zA-Z0-9_=,:.+\\- ]+) of next month$")
  public void bookFlightFromOriginToDestination(String origin, String destination, String date) {
    automationHelper.bookFlight(origin, destination, date);
  }

  @Then("^I assert all via soft assert$")
  public void assertSoftAssert() throws Exception {
    try {
      Constants.SOFT_ASSERT.get().assertAll();
    } catch (Exception e) {
      throw new Exception("Unable to do soft asserts, Exceptions: " + e);
    }
  }

  @Then("^I close the test driver$")
  public void iCloseTheTestDriver() {
    automationHelper.quitTestDriver();
  }
}
