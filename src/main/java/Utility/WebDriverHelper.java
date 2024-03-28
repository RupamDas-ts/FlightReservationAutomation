package Utility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverHelper {
  private WebDriver driver;

  public void setDriver(WebDriver driver){
    this.driver = driver;
  }

  public void getURL(String url){
    driver.get(url);
  }

  public void waitForElement(String[] locator, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    String using = locator[0].toLowerCase();
    String locatorValue = locator[1];

    if (using.contentEquals("id")) {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
    } else if (using.contentEquals("class")) {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.className(locatorValue)));
    } else if (using.contentEquals("name")) {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.name(locatorValue)));
    } else if (using.contentEquals("xpath")) {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
    } else if (using.contentEquals("css")) {
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locatorValue)));
    }
  }

  public WebElement getElement(String[] locator, int waitTime) {
    if (waitTime > 0) {
      waitForElement(locator, waitTime);
    }
    String using = locator[0].toLowerCase();
    String locatorValue = locator[1];
    WebElement ele = null;
    if (using.contentEquals("id")) {
      ele = driver.findElement(By.id(locatorValue));
    } else if (using.contentEquals("class")) {
      ele = driver.findElement(By.className(locatorValue));
    } else if (using.contentEquals("name")) {
      ele = driver.findElement(By.name(locatorValue));
    } else if (using.contentEquals("xpath")) {
      ele = driver.findElement(By.xpath(locatorValue));
    } else if (using.contentEquals("css")) {
      ele = driver.findElement(By.cssSelector(locatorValue));
    } else if (using.contentEquals("tagName")) {
      ele = driver.findElement(By.tagName(locatorValue));
    }
    return ele;
  }

  public void waitForElementTillClickable(String[] locator, int timeout) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    String using = locator[0].toLowerCase();
    String locatorValue = locator[1];
    WebElement ele;

    if (using.contentEquals("id")) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
      ele = driver.findElement(By.id(locatorValue));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
    } else if (using.contentEquals("class")) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
      ele = driver.findElement(By.className(locatorValue));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
    } else if (using.contentEquals("name")) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
      ele = driver.findElement(By.name(locatorValue));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
    } else if (using.contentEquals("xpath")) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
      ele = driver.findElement(By.xpath(locatorValue));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
    } else if (using.contentEquals("css")) {
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(locatorValue))));
      ele = driver.findElement(By.cssSelector(locatorValue));
      wait.until(ExpectedConditions.elementToBeClickable(ele));
    }
  }

  public void clickOnElement(WebElement ele) {
    ele.click();
  }

  public void clickOnElement(String[] locator) {
    waitForElementTillClickable(locator, 30);
    WebElement ele = getElement(locator, 0);
    clickOnElement(ele);
  }

  public void waitForElementToBeVisible(String[] locator, int timeout) {
    Duration setImplicitWait = driver.manage().timeouts().getImplicitWaitTimeout();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0));
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
      String using = locator[0].toLowerCase();
      String locatorValue = locator[1];
      if (using.contentEquals("id")) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
      } else if (using.contentEquals("class")) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
      } else if (using.contentEquals("name")) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
      } else if (using.contentEquals("xpath")) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
      } else if (using.contentEquals("css")) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
      }
      driver.manage().timeouts().implicitlyWait(setImplicitWait);
    } catch (Exception e) {
      driver.manage().timeouts().implicitlyWait(setImplicitWait);
      throw e;
    }
  }
  public boolean isDisplayed(String[] locator, int timeOut) {
    String methodName = new Object() {
    }.getClass().getEnclosingMethod().getName();
    try {
      waitForElementToBeVisible(locator, timeOut);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public void sendKeys(String[] locator, String text) {
    WebElement ele = getElement(locator,2);
    ele.sendKeys(text);
  }

}
