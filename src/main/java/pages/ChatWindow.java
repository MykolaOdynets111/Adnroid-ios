package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.NoSuchElementException;

public class ChatWindow extends BasePage {
    public ChatWindow(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ChatWindow sendMessage(String message, String platform, AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath("//android.widget.EditText[@resource-id='Composer:input']").sendKeys(message);
            driver.findElementByXPath("//android.widget.EditText[@resource-id='Composer:input']/../following-sibling::*").click();
        }
        if (isPlatformIos(platform)) {
            driver.findElementByXPath("//XCUIElementTypeTextView[@name='Composer:input']").sendKeys(message);
            driver.findElementByXPath("//XCUIElementTypeOther[@name='Composer:send']").click();
        }
        return this;
    }

    public boolean isChatContainsMessage(String message, String platform, AppiumDriver<MobileElement> driver) {
        if (isPlatformIos(platform)) {
            try {
                driver.findElementByXPath(String.format("//XCUIElementTypeOther[starts-with(@name,'%s')]", message)).isEnabled();
                return true;
            } catch (NoSuchElementException exception) {
                logger.info(String.format("the message '%s' in the IOS platform was not found", message));
                return false;
            }
        }
        if (isPlatformAndroid(platform)) {
            try {
                driver.findElementByXPath(String.format("//android.view.ViewGroup[starts-with(@content-desc,'%s')]", message)).isEnabled();
                return true;
            } catch (NoSuchElementException exception) {
                logger.info(String.format("the message '%s' in the Android platform was not found", message));
                return false;
            }
        } else return false;
    }

}


