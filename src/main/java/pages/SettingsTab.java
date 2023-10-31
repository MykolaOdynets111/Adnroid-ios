package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class SettingsTab extends BasePage {

    public SettingsTab(AndroidDriver<MobileElement> androidDriver) {
        super(androidDriver);
    }

    public SettingsTab openProfile() {
        androidDriver.findElementByXPath("//android.widget.TextView[@resource-id='Settings:status']").click();
        return this;
    }

    public String getProfileName() {
        return androidDriver.findElementByXPath("//android.widget.EditText[@resource-id='Profile:name:TextInput']")
                .getText();
    }

    public SettingsTab closeProfile() {
        androidDriver.findElementByXPath("//android.widget.TextView[@text='']").click();
        return this;
    }
}
