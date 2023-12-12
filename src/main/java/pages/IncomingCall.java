package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class IncomingCall extends BasePage {

    private static final By IOS_ACCEPT_BTN = By.xpath("//XCUIElementTypeButton[@name='Accept']");
    private static final By ANDROID_ACCEPT_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/ivAcceptCall']");
    private static final By IOS_DECLINE_BTN = By.xpath("//XCUIElementTypeButton[@name='Decline']");
    private static final By ANDROID_DECLINE_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/ivDeclineCall']");
    private static final By ANDROID_AVATAR = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/imageView2']");
    private static final String ANSWER_BTN = "Answer call";

    public IncomingCall(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ActiveCall answerCall(AppiumDriver<MobileElement> driver) {
        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_ACCEPT_BTN, ANSWER_BTN);
        }
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_ACCEPT_BTN, ANSWER_BTN);
        }
        return new ActiveCall(driver);
    }

    public boolean isCallRequested(AppiumDriver<MobileElement> driver) {
        boolean isActive = false;
        if (isPlatformIos(driver)) {
            isActive = isElemEnabledBy(driver, IOS_ACCEPT_BTN, ANSWER_BTN) &&
                    isElemEnabledBy(driver, IOS_DECLINE_BTN, "Decline call");
        }
        if (isPlatformAndroid(driver)) {
            isActive = isElemEnabledBy(driver, ANDROID_DECLINE_BTN, "Decline call") &&
                    isElemEnabledBy(driver, ANDROID_ACCEPT_BTN, ANSWER_BTN) &&
                    isElemEnabledBy(driver, ANDROID_AVATAR, "Avatar icon");
        }
        return isActive;
    }

}
