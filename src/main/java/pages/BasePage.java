package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class BasePage {

    protected AppiumDriver<MobileElement> driver;

    public BasePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public SettingsTab openSettingsTab(AppiumDriver<MobileElement> driver, String platform) {
        if (isPlatformIos(platform)) {
            driver.findElementByName("SETTINGS, tab, 4 of 4").click();
        }
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath("//android.widget.TextView[@text='SETTINGS']").click();
        }
        return new SettingsTab(driver);
    }

    public ChatTab openChatsTab(AppiumDriver<MobileElement> driver, String platform) {
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath("//android.widget.TextView[@text='CHATS']").click();
        }
        if (isPlatformIos(platform)) {
            driver.findElementByName("CHATS, tab, 1 of 4").click();
        }
        return new ChatTab(driver);
    }

    protected boolean isPlatformAndroid(String platform){
        return platform.equalsIgnoreCase("Android");
    }

    protected boolean isPlatformIos(String platform){
        return platform.equalsIgnoreCase("IOS");
    }
}
