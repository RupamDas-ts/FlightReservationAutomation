package Utility;

import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utility.Constants.*;

public class AutomationHelper {
  API_Helper apiHelper = new API_Helper();
  WebDriverHelper webDriverHelper = new WebDriverHelper();
  WebDriver driver;
  public String getRandomEmailID(){
    Response response = apiHelper.getResponse(apiToFetchRandomEmail,null,200,"");
    System.out.println("Response of API to generate random email: "+response.asPrettyString());
    String[] arrayOfStrings = response.jsonPath().getList("$").toArray(new String[0]);
    if (arrayOfStrings != null && arrayOfStrings.length > 0) {
      emailAddress.set(arrayOfStrings[0]);
      return arrayOfStrings[0];
    }
    emailAddress.set("");
    return "";
  }

  public static String extractOTP(String text) {
    String regexPattern = "OTP is (\\d+)";
    Pattern pattern = Pattern.compile(regexPattern);
    Matcher matcher = pattern.matcher(text);
    if (matcher.find()) {
      System.out.println("OTP is: "+matcher.group(1));
      return matcher.group(1);
    } else {
      return null;
    }
  }

  public String getOtp(String email){
    String name = email.split("@")[0];
    String domain = email.split("@")[1];
    String uriForMailBox = apiToFetchAllMessages.replace("<name>",name).replace("<domain>",domain);
    System.out.println("URI to fetch the mailbox-> "+uriForMailBox);
    int idOfLastMessage = apiHelper.getResponse(uriForMailBox).jsonPath().getInt("[-1].id");
    String uriToFetchMessageDetails = apiToFetchSingleMessages.replace("<name>",name).replace("<domain>",domain).replace("<message_id>",String.valueOf(idOfLastMessage));
    System.out.println("URI to fetch details of one specific messgae with id: "+idOfLastMessage+"-> "+uriToFetchMessageDetails);
    String bodyOfTheMail = apiHelper.getResponse(uriToFetchMessageDetails).jsonPath().get("$.textBody").toString();
    System.out.println("Body of the mail: "+bodyOfTheMail);
    return extractOTP(bodyOfTheMail);
  }

  public HashMap<String, String> getHasMapFromString(String capsAsString){
    String[] array = capsAsString.split(",");
    if(array.length == 0){
      return null;
    }
    HashMap<String, String> hashMap = new HashMap<>();
    for (String string : array){
      hashMap.put(string.split("=")[0].trim(),string.split("=")[1].trim());
    }
    return hashMap;
  }

  public DesiredCapabilities getCapabilitiesObject(HashMap<String, String> caps){
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("LT:Options", caps);
    return capabilities;
  }

  public void getWebDriverUsingGivenCaps(String capsString){
    System.out.println("Caps passed as String:- "+capsString);
    HashMap<String, String> caps = getHasMapFromString(capsString);
    DesiredCapabilities capabilities = getCapabilitiesObject(caps);
    String gridURL = HTTP + LT_USER_NAME + ":" + LT_ACCESS_KEY + LT_HUB_URL;
    System.out.println("Hub URL: "+gridURL);
    driver = RemoteWebDriver.builder().oneOf(capabilities).address(gridURL).build();
    test_driver.set(driver);
    webDriverHelper.setDriver(driver);
  }

  public void openMMTSignUpPage(){
    webDriverHelper.getURL("https://www.makemytrip.com/flights");
    if(webDriverHelper.isDisplayed(notificationCloseButton,5)){
      webDriverHelper.clickOnElement(notificationCloseButton);
    }
    if(webDriverHelper.isDisplayed(homePageHeading,2))
      System.out.println("Page open successful.");
    webDriverHelper.clickOnElement(createAccountOrSignInButton);
    webDriverHelper.clickOnElement(createAccountWithEmail);
    if (webDriverHelper.isDisplayed(emailInput,2)){
      System.out.println("Login Form is opened.");
      webDriverHelper.sendKeys(emailInput,emailAddress.get());
      webDriverHelper.clickOnElement(continueToOTPButton);
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      String otp = getOtp(emailAddress.get());
      webDriverHelper.sendKeys(otpInput,otp);
      webDriverHelper.clickOnElement(verifyAndCreateButton);
    }else {
      System.out.println("Login Form is not opened.");
    }
  }
}
