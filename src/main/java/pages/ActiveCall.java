package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;

import static io.appium.java_client.touch.offset.PointOption.point;

public class ActiveCall extends BasePage {

    private static final By ANDROID_MUTE_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/micButton']");
    private static final By ANDROID_SPEAKER_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/speakerButton']");
    private static final By ANDROID_TIMER = By.xpath("//android.widget.TextView[@resource-id='io.shadow.chat.staging:id/tvInfo']");
    private static final By ANDROID_STOP_CALL_BTN = By.xpath("//android.widget.ImageView[@resource-id='io.shadow.chat.staging:id/ivDeclineCall']");
    private static final String IOS_ABONENT_NAME = "//XCUIElementTypeOther[contains(@name, '%s Hubnub Call')]";


    public ActiveCall(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public boolean isCallInProgress(AppiumDriver<MobileElement> driver, String incomingAbonentName) {
        boolean isCallInProgress = false;
        if (isPlatformAndroid(driver)) {
            isCallInProgress = isElemEnabledBy(driver, ANDROID_MUTE_BTN, "mute button") &&
                    isElemEnabledBy(driver, ANDROID_SPEAKER_BTN, "speaker button") &&
                    isElemEnabledBy(driver, ANDROID_TIMER, " call timer") &&
                    isElemEnabledBy(driver, ANDROID_STOP_CALL_BTN, " stop button");
        }
        if (isPlatformIos(driver)) {
            isCallInProgress = isElemEnabledBy(driver, By.xpath(String.format(IOS_ABONENT_NAME, incomingAbonentName)), "incoming abonents name");
        }
        return isCallInProgress;
    }

    public void stopCall(AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_STOP_CALL_BTN, "Stop call button");
        }
        if (isPlatformIos(driver)) {
            new TouchAction<>(driver).tap(point(317, 782)).perform();
        }
    }

}
