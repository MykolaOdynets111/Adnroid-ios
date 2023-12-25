package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;

import static io.appium.java_client.touch.offset.PointOption.point;
import static utillities.Utilities.isPlatformAndroid;
import static utillities.Utilities.isPlatformIos;

public class IncomingCall extends BasePage {

    private static final By ANDROID_ACCEPT_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/ivAcceptCall']");
    private static final By ANDROID_DECLINE_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/ivDeclineCall']");
    private static final By ANDROID_AVATAR = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/imageView2']");
    private static final String IOS_INCOMING_CONSUMER_NAME_XPATH = "//XCUIElementTypeOther[starts-with(@name,'%s')]";
    private static final String ANSWER_BTN = "Answer call";

    public IncomingCall(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ActiveCall answerCall(AppiumDriver<MobileElement> driver) throws InterruptedException {
        if (isPlatformIos(driver)) {
            Thread.sleep(4000);
            new TouchAction<>(driver).tap(point(200, 60)).perform();
            Thread.sleep(2000);
            new TouchAction<>(driver).tap(point(317, 782)).perform();
        }
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_ACCEPT_BTN, ANSWER_BTN);
        }
        return new ActiveCall(driver);
    }

    public boolean isCallRequested(AppiumDriver<MobileElement> driver, String incomingConsumerName) {
        boolean isActive = false;
        if (isPlatformIos(driver)) {
            isActive = isElemEnabledBy(driver, By.xpath(String.format(IOS_INCOMING_CONSUMER_NAME_XPATH, incomingConsumerName)), "consumer name");
        }
        if (isPlatformAndroid(driver)) {
            isActive = isElemEnabledBy(driver, ANDROID_DECLINE_BTN, "Decline call") &&
                    isElemEnabledBy(driver, ANDROID_ACCEPT_BTN, ANSWER_BTN) &&
                    isElemEnabledBy(driver, ANDROID_AVATAR, "Avatar icon");
        }
        return isActive;
    }

}
