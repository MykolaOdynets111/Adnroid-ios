package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BasePage {

    protected AndroidDriver<MobileElement> androidDriver;

    public BasePage(AndroidDriver<MobileElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public SettingsTab openSettingsTab(AndroidDriver<MobileElement> androidDriver) {
        androidDriver.findElementByXPath("//android.widget.TextView[@text='SETTINGS']").click();
        return new SettingsTab(androidDriver);
    }

    public ChatTab openChatsTab(AndroidDriver<MobileElement> androidDriver) {
        androidDriver.findElementByXPath("//android.widget.TextView[@text='CHATS']").click();
        return new ChatTab(androidDriver);
    }
}
