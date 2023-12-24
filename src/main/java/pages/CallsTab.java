package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class CallsTab extends BasePage {

    private static final String ANDROID_CONSUMER_NAME = "//android.widget.TextView[@text='%s']";
    private static final String IOS_CONSUMER_NAME = "//XCUIElementTypeOther[starts-with(@name,'%s')]";

    public CallsTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void makeCallByName(String name, AppiumDriver<MobileElement> driver) throws InterruptedException {
        if (isPlatformAndroid(driver)) {
            Thread.sleep(1000);
            clickLastElementBy(driver, By.xpath(String.format(ANDROID_CONSUMER_NAME, name)), "Name: " + name);
        }
        if (isPlatformIos(driver)) {
            clickLastElementBy(driver, By.xpath(String.format(IOS_CONSUMER_NAME, name)), "Name: " + name);
        }
    }

}
