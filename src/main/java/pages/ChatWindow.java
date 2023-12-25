package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static utillities.Utilities.isPlatformAndroid;
import static utillities.Utilities.isPlatformIos;

public class ChatWindow extends BasePage {
    public ChatWindow(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    private static final By ANDROID_MESSAGE_INPUT = By.xpath("//android.widget.EditText[@resource-id='Composer:input']");
    private static final By IOS_MESSAGE_INPUT = By.xpath("//XCUIElementTypeTextView[@name='Composer:input']");
    private static final By ANDROID_SEND_BTN = By.xpath("//android.widget.EditText[@resource-id='Composer:input']/../following-sibling::*");
    private static final By IOS_SEND_BTN = By.xpath("//XCUIElementTypeOther[@name='Composer:send']");
    private static final String IOS_RECEIVED_MSG_XPATH = "//XCUIElementTypeOther[starts-with(@name,'%s')]";

    private static final String ANDROID_RECEIVED_MSG_XPATH = "//android.view.ViewGroup[starts-with(@content-desc,'%s')]";

    public ChatWindow sendMessage(String message, AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            putTextToElemBy(driver, ANDROID_MESSAGE_INPUT, message, "input message field");
            clickElemBy(driver, ANDROID_SEND_BTN, "send message button");
        }
        if (isPlatformIos(driver)) {
            putTextToElemBy(driver, IOS_MESSAGE_INPUT, message, "input message field");
            clickElemBy(driver, IOS_SEND_BTN, "send message button");
        }
        return this;
    }

    public boolean isChatContainsMessage(String message, AppiumDriver<MobileElement> driver) {
        boolean isMessagePresent = false;
        if (isPlatformIos(driver)) {
            isMessagePresent = isElemEnabledBy(driver, By.xpath(String.format(IOS_RECEIVED_MSG_XPATH, message)), "chat name");
        }
        if (isPlatformAndroid(driver)) {
            isMessagePresent = isElemEnabledBy(driver, By.xpath(String.format(ANDROID_RECEIVED_MSG_XPATH, message)), "chat name");
        }
        return isMessagePresent;
    }

}


