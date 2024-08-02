package Utility;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final ThreadLocal<String> userName = new ThreadLocal<>();
    public static final String HTTP = "https://";
    public static final ThreadLocal<WebDriver> test_driver = new ThreadLocal<>();
    public static final ThreadLocal<String> test_session_id = new ThreadLocal<>();
    public static final ThreadLocal<SoftAssert> SOFT_ASSERT = new ThreadLocal<>();
    public static final ThreadLocal<String> emailAddress = new ThreadLocal<>();
    public static final String password = "123456789@a";
    public static final String apiToFetchRandomEmail = "https://www.1secmail.com/api/v1/?action=genRandomMailbox&count=1";
    public static final String apiToFetchAllMessages = "https://www.1secmail.com/api/v1/?action=getMessages&login=<name>&domain=<domain>";
    public static final String apiToFetchSingleMessages = "https://www.1secmail.com/api/v1/?action=readMessage&login=<name>&domain=<domain>&id=<message_id>";

    public static final String MMT_URL = "https://www.makemytrip.com/flights";

    /*  Locators  */
    public static final String[] notificationCloseButton = new String[]{"xpath", "//div[text()='Accept all']"};
    public static final String[] homePageHeading = new String[]{"css",
            "a[aria-label='Go to the cheapflights homepage']"};
    public static final String[] createAccountOrSignInButton = new String[]{"css",
            "div[class*='HeaderAccount'] div[role='button']"};
    public static final String[] createAccountWithEmail = new String[]{"xpath", "//div[contains(text(),'Continue with email')]/ancestor::button"};
    public static final String[] emailInput = new String[]{"css", "input[placeholder='Email address']"};
    public static final String[] acceptCreatingNewAccount = new String[]{"xpath", "//div[text()='Continue']"};
    public static final String[] continueToOTPButton = new String[]{"xpath", "//div[text()='Create your account']"};
    public static final String[] removeExistingOrgField = new String[]{"xpath",
            "//*[contains(@class, 'formField') and contains(@class, 'origin')]//div[contains(@class, 'close')]"};
    public static final String[] originInputField = new String[]{"css", "input[aria-label='Flight origin input']"};
    public static final String[] destInputField = new String[]{"css", "input[aria-label='Flight destination input']"};
    public static final String[] desiredLocation = new String[]{"xpath", "//*[contains(@id, '<location>')]"};
    public static final String[] dateInput = new String[]{"css", "div[aria-label='Start date']"};
    public static final String[] dateLocator = new String[]{"xpath", "//div[contains(@aria-label, '<date>')]"};
    public static final String[] searchButton = new String[]{"css", "button[aria-label='Search']"};
    public static final String[] results = new String[]{"xpath", "//div[contains(@class,'results-list')]"};

    /* Test Env Setup */
    public static final String TEST_ENV_NAME = System.getProperty("ENV_NAME") == null ?
            "LambdaTest" :
            System.getProperty("ENV_NAME");

    protected static Map<String, String> config = getEnvConfig();

    public static Map<String, String> getEnvConfig() {
        File yamlFile = new File("src/main/java/TestConfigs/Cucumber.yaml");
        Yaml ymlFileReader = new Yaml();
        Object envValue = null;
        try {
            InputStream inStr = new FileInputStream(yamlFile);
            Map<String, Object> ymlObj = ymlFileReader.load(inStr);
            envValue = ymlObj.get(TEST_ENV_NAME);
            Map<String, String> envConfig = (HashMap<String, String>) envValue;
            return envConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return (Map<String, String>) envValue;
    }
}
