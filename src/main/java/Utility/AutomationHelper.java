package Utility;

import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utility.Constants.*;

public class AutomationHelper {
  API_Helper apiHelper = new API_Helper();
  WebDriverHelper webDriverHelper;
  WebDriver driver;

  public static String extractOTP(String text) {
    String regexPattern = "(\\d+) is your Cheapflights verification code";
    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(text);
    if (matcher.find()) {
      System.out.println("OTP is: " + matcher.group(1));
      return matcher.group(1);
    } else {
      return null;
    }
  }

  public static String generateDate(String day) {
    LocalDate currentDate = LocalDate.now();
    int inputDay = Integer.parseInt(day);
    LocalDate nextMonth = currentDate.plusMonths(1);
    LocalDate generatedDate = LocalDate.of(nextMonth.getYear(), nextMonth.getMonthValue(), inputDay);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
    String formattedDate = generatedDate.format(formatter);
    return formattedDate;
  }

  public String getRandomEmailID() {
    Response response = apiHelper.getResponse(apiToFetchRandomEmail, null, 200, "");
    System.out.println("Response of API to generate random email: " + response.asPrettyString());
    String[] arrayOfStrings = response.jsonPath().getList("$").toArray(new String[0]);
    if (arrayOfStrings != null && arrayOfStrings.length > 0) {
      emailAddress.set(arrayOfStrings[0]);
      return arrayOfStrings[0];
    }
    emailAddress.set("");
    return "";
  }

  public String getOtp(String email) {
    String name = email.split("@")[0];
    String domain = email.split("@")[1];
    String uriForMailBox = apiToFetchAllMessages.replace("<name>", name).replace("<domain>", domain);
    System.out.println("URI to fetch the mailbox-> " + uriForMailBox);
    int idOfLastMessage = apiHelper.getResponse(uriForMailBox).jsonPath().getInt("[-1].id");
    String uriToFetchMessageDetails = apiToFetchSingleMessages.replace("<name>", name).replace("<domain>", domain)
      .replace("<message_id>", String.valueOf(idOfLastMessage));
    System.out.println(
      "URI to fetch details of one specific messgae with id: " + idOfLastMessage + "-> " + uriToFetchMessageDetails);
    String bodyOfTheMail = apiHelper.getResponse(uriToFetchMessageDetails).jsonPath().get("subject").toString();
    System.out.println("Body of the mail: " + bodyOfTheMail);
    return extractOTP(bodyOfTheMail);
  }

  public HashMap<String, String> getHasMapFromString(String capsAsString) {
    String[] array = capsAsString.split(",");
    if (array.length == 0) {
      return null;
    }
    HashMap<String, String> hashMap = new HashMap<>();
    for (String string : array) {
      hashMap.put(string.split("=")[0].trim(), string.split("=")[1].trim());
    }
    return hashMap;
  }

  public DesiredCapabilities getCapabilitiesObject(HashMap<String, String> caps) {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    if (TEST_ENV_NAME.equals("LambdaTest")) {
      capabilities.setCapability("LT:Options", caps);
    } else if (TEST_ENV_NAME.equals("SauceLabs")) {
      capabilities.setCapability("sauce:options", caps);
    }
    return capabilities;
  }

  public void getWebDriverUsingGivenCaps(String capsString) {
    System.out.println("Caps passed as String:- " + capsString);
    HashMap<String, String> caps = getHasMapFromString(capsString);
    DesiredCapabilities capabilities = getCapabilitiesObject(caps);
    if (TEST_ENV_NAME.equals("local")) {
      switch (caps.get("browserName").toLowerCase()) {
      case "chrome":
        driver = new ChromeDriver();
        break;
      case "firefox":
        driver = new FirefoxDriver();
        break;
      }
    } else {
      String gridURL = HTTP + config.get("userName") + ":" + config.get("accessKey") + LT_HUB_URL;
      System.out.println("Hub URL: " + gridURL);
      driver = RemoteWebDriver.builder().oneOf(capabilities).address(gridURL).build();
      test_driver.set(driver);
    }
    webDriverHelper = new WebDriverHelper(driver);
  }

  public void openMMTSignUpPage() {
    webDriverHelper.getURL("https://www.in.cheapflights.com/");
    if (webDriverHelper.isDisplayed(homePageHeading, 5)) {
      System.out.println("Opening page successful.");
    }
  }

  public void createNewMMTAccount() {
    if (webDriverHelper.isDisplayed(notificationCloseButton, 5)) {
      webDriverHelper.clickOnElement(notificationCloseButton);
    }
    webDriverHelper.clickOnElement(createAccountOrSignInButton);
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    webDriverHelper.clickOnElement(createAccountWithEmail);
    webDriverHelper.sendKeys(emailInput, emailAddress.get());
    webDriverHelper.clickOnElement(acceptCreatingNewAccount);
    webDriverHelper.clickOnElement(continueToOTPButton);
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    String otp = getOtp(emailAddress.get());
    webDriverHelper.sendKeysViaAction(otp);
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void bookFlight(String origin, String dest, String date) {
    if (webDriverHelper.isDisplayed(removeExistingOrgField, 2)) {
      webDriverHelper.clickOnElement(removeExistingOrgField);
    }
    String[] originLocator = desiredLocation.clone();
    originLocator[1] = originLocator[1].replace("<location>", origin);
    String[] destLocator = desiredLocation.clone();
    destLocator[1] = destLocator[1].replace("<location>", dest);
    webDriverHelper.sendKeys(originInputField, origin);
    webDriverHelper.clickOnElement(originLocator);
    webDriverHelper.sendKeys(destInputField, dest);
    webDriverHelper.clickOnElement(destLocator);
    webDriverHelper.clickOnElement(dateInput);
    String requiredDate = generateDate(date);
    String[] locatorForDate = dateLocator.clone();
    locatorForDate[1] = locatorForDate[1].replace("<date>", requiredDate);
    webDriverHelper.clickOnElement(locatorForDate);
    webDriverHelper.clickOnElement(searchButton);
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    webDriverHelper.switchToTab(0, 1);
    if (webDriverHelper.isDisplayed(results, 5)) {
      System.out.println("Searching for Flight is success.");
    }
  }
}
