package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.List;

public class ChatTab extends BasePage {
    public ChatTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ChatWindow openChatByName(String name, String platform, AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath(String.format("//android.widget.TextView[@text='%s']", name)).click();
        }
        if (isPlatformIos(platform)) {
            List<MobileElement> list = driver.findElementsByXPath(String.format("//XCUIElementTypeOther[starts-with(@name,'%s')]", name));
            list.get(list.size() - 1).click();
        }
        return new ChatWindow(driver);
    }
}


