package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SettingsTab extends BasePage {

    public SettingsTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public SettingsTab openProfile(String platform) {

        if (isPlatformIos(platform)) {
            driver.findElementByName("Settings:contactAvatar").click();
        }
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath("//*[@resource-id='Settings:contactAvatar']").click();
        }
        return this;
    }

    public String getProfileName(String platform) {
        if (isPlatformAndroid(platform)) {
            return driver.findElementByXPath("//android.widget.EditText[@resource-id='Profile:name:TextInput']").getText();
        }
        if (isPlatformIos(platform)) {
            return
                    driver.findElementByAccessibilityId("Profile:name:TextInput").getText();
        } else {
            return "wrong platform";
        }
    }

    public String getStatus(String platform) {
        if (isPlatformAndroid(platform)) {
            return driver.findElementByXPath("//android.widget.EditText[@resource-id='Profile:status:TextInput']")
                    .getText();
        }
        if (isPlatformIos(platform)) {
            return driver.findElementByName("Profile:status:TextInput").getText(); //IOS
        } else return "wrong platform";
    }

    public SettingsTab closeProfile(String platform) {
        if (isPlatformIos(platform)) {
            driver.findElementByXPath("//XCUIElementTypeOther[@name='\uEB4D']").click();
        }
        if (isPlatformAndroid(platform)) {
            driver.findElementByXPath("//android.widget.TextView[@text='\uEB4D']").click();
        }
        return this;
    }
}
