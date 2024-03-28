package StepDefinations;

import Utility.API_Helper;
import Utility.AutomationHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AutomationStepDefinations {
  AutomationHelper automationHelper = new AutomationHelper();
  @Given("^Setup User Details for new Account creation$")
  public void setupAccountDetails(){
    String emails = automationHelper.getRandomEmailID();
    if(emails.isEmpty())
      System.out.println("Unable too generate Random Email.");
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
  }

}
