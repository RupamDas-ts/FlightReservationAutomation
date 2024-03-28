package Utility;

import org.openqa.selenium.WebDriver;

public class Constants {
  public static final String HTTP = "https://";
  public static final String LT_USER_NAME = "rupamd";
  public static final String LT_ACCESS_KEY = "g8bSPewKA2UPOBkcfGMcv1Vmc8N9CM11gIDUUyL9q9a8OpxUwc";
  public static final ThreadLocal<WebDriver> test_driver = new ThreadLocal<>();
  public static final ThreadLocal<String> emailAddress = new ThreadLocal<>();
  public static final String password = "123456789@a";
  public static final String apiToFetchRandomEmail = "https://www.1secmail.com/api/v1/?action=genRandomMailbox&count=1";
  public static final String apiToFetchAllMessages = "https://www.1secmail.com/api/v1/?action=getMessages&login=<name>&domain=<domain>";
  public static final String apiToFetchSingleMessages = "https://www.1secmail.com/api/v1/?action=readMessage&login=<name>&domain=<domain>&id=<message_id>";
  public static final String LT_HUB_URL = "@hub.lambdatest.com/wd/hub";


  public static final String MMT_URL = "https://www.makemytrip.com/flights";

  /*  Locators  */
  public static final String[] notificationCloseButton = {"class","close"};
  public static final String[] homePageHeading = {"css","img[alt='Make My Trip']"};
  public static final String[] createAccountOrSignInButton = {"css","li[data-cy='account']"};
  public static final String[] createAccountWithEmail = {"css", "div[data-cy='signInByMailButton']"};
  public static final String[] emailInput = {"css", "input[placeholder='Enter Email Address']"};
  public static final String[] continueToOTPButton = {"css", "button[data-cy='continueBtn']"};
  public static final String[] otpInput = {"css", "input[placeholder='Enter OTP here']"};
  public static final String[] verifyAndCreateButton = {"css", "button[data-cy='verifyAndCreate']"};
}
