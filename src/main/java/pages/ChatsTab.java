package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;


public class ChatsTab extends BasePage {
    private static final String ANDROID_CHAT_NAME = "//android.widget.TextView[@text='%s']";
    private static final String IOS_CHAT_NAME = "//XCUIElementTypeOther[starts-with(@name,'%s')]";


    public ChatsTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public ChatWindow openChatByName(String name, AppiumDriver<MobileElement> driver) {
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, By.xpath(String.format(ANDROID_CHAT_NAME, name)), "chat name");
        }
        if (isPlatformIos(driver)) {
            clickLastElementBy(driver, By.xpath(String.format(IOS_CHAT_NAME, name)), "Name: " + name);
        }
        return new ChatWindow(driver);
    }
}


