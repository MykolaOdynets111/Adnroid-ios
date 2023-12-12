package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class CallsTab extends BasePage {

    private static final String ANDROID_ABONENT_NAME = "//android.widget.TextView[@text='%s']";
    private static final String IOS_ABONENT_NAME = "//XCUIElementTypeOther[starts-with(@name,'%s')]";

    public CallsTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public void makeCallByName(String name, AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickLastElementBy(driver, By.xpath(String.format(ANDROID_ABONENT_NAME, name)), "Name: " + name);
        }
        if (isPlatformIos(driver)) {
            clickLastElementBy(driver, By.xpath(String.format(IOS_ABONENT_NAME, name)), "Name: " + name);
        }
    }

}
