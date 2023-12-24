package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

public class BasePage {

    protected AppiumDriver<MobileElement> driver;

    private static final By ANDROID_CHATS_BTN = By.xpath("//android.widget.TextView[@text='CHATS']");
    private static final By IOS_CHATS_BTN = By.name("CHATS, tab, 1 of 4");
    private static final By ANDROID_SETTINGS_BTN = By.xpath("//android.widget.TextView[@text='SETTINGS']");
    private static final By IOS_SETTINGS_BTN = By.name("SETTINGS, tab, 4 of 4");
    private static final By ANDROID_CALLS_BTN = By.xpath("//android.widget.TextView[@text='CALLS']");
    private static final By IOS_CALLS_BTN = By.name("CALLS, tab, 2 of 4");


    public BasePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public SettingsTab openSettingsTab(AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_SETTINGS_BTN, "Settings button");
        }
        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_SETTINGS_BTN, "Settings button");
        }
        return new SettingsTab(driver);
    }

    public ChatsTab openChatsTab(AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_CHATS_BTN, "Chats button");
        }
        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_CHATS_BTN, "Chats button");
        }
        return new ChatsTab(driver);
    }

    public CallsTab openCallsTab(AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_CALLS_BTN, "Calls button");
        }
        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_CALLS_BTN, "Calls button");
        }
        return new CallsTab(driver);
    }

    protected void clickElemBy(MobileDriver<MobileElement> driver, By element, String elemName) {
        try {
            driver.findElement(element).click();
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail("Cannot click '" + elemName + "' because button is not clickable.");
        }
    }

    protected void putTextToElemBy(MobileDriver<MobileElement> driver, By element, String text, String elemName) {
        try {
            driver.findElement(element).sendKeys(text);
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail("Cannot put text to '" + elemName + "' because element does not exist.");
        }

    }

    protected boolean isElemEnabledBy(MobileDriver<MobileElement> driver, By element, String elemName) {
        try {
            return driver.findElement(element).isEnabled();
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail(errorDescription(elemName));
            return false;
        }
    }

    protected String getTextBy(MobileDriver<MobileElement> driver, By element, String elemName) {
        try {
            return driver.findElement(element).getText();
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail(errorDescription(elemName));
            return null;
        }
    }

    protected void clickLastElementBy(MobileDriver<MobileElement> driver, By element, String elemName) {
        try {
            List<MobileElement> elementsList = driver.findElements(element);
            elementsList.get(elementsList.size()-1).click();
        } catch (TimeoutException | NoSuchElementException |IndexOutOfBoundsException e) {
            Assert.fail(errorDescription(elemName));
        }
    }
    protected boolean isPlatformAndroid(AppiumDriver<MobileElement> driver) {
        return Objects.requireNonNull(driver.getPlatformName()).equalsIgnoreCase("Android");
    }

    protected boolean isPlatformIos(AppiumDriver<MobileElement> driver) {
        return Objects.requireNonNull(driver.getPlatformName()).equalsIgnoreCase("IOS");
    }

    private String errorDescription(String elemName){
        return "'" + elemName + "' does not exist.";
    }
}
