package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class SettingsTab extends BasePage {
    private static final By ANDROID_PROFILE_AVATAR = By.xpath("//*[@resource-id='Settings:contactAvatar']");
    private static final By IOS_PROFILE_AVATAR = By.name("Settings:contactAvatar");
    private static final By ANDROID_PROFILE_NAME_INPUT = By.xpath("//android.widget.EditText[@resource-id='Profile:name:TextInput']");
    private static final By IOS_PROFILE_NAME_INPUT = By.xpath("//XCUIElementTypeTextField[@name='Profile:name:TextInput']");
    private static final By ANDROID_STATUS_INPUT = By.xpath("//android.widget.EditText[@resource-id='Profile:status:TextInput']");
    private static final By IOS_STATUS_INPUT = By.xpath("//XCUIElementTypeTextField[@name='Profile:status:TextInput']");
    private static final By ANDROID_CLOSE_PROFILE_BTN = By.xpath("//android.widget.TextView[@text='\uEB4D']");
    private static final By IOS_CLOSE_PROFILE_BTN = By.xpath("//XCUIElementTypeOther[@name='\uEB4D']");

    public SettingsTab(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public SettingsTab openProfile() {

        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_PROFILE_AVATAR, "Profile avatar");
        }
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_PROFILE_AVATAR, "Profile avatar");
        }
        return this;
    }

    public String getProfileName() {
        String profileName = null;
        if (isPlatformAndroid(driver)) {
            profileName = getTextBy(driver, ANDROID_PROFILE_NAME_INPUT, "Account name");
        }
        if (isPlatformIos(driver)) {
            profileName = getTextBy(driver, IOS_PROFILE_NAME_INPUT, "Account name");
        }
        return profileName;
    }

    public String getStatus() {
        String profileStatus = null;
        if (isPlatformAndroid(driver)) {
            profileStatus = getTextBy(driver, ANDROID_STATUS_INPUT, "Profile Status");
        }
        if (isPlatformIos(driver)) {
            profileStatus = getTextBy(driver, IOS_STATUS_INPUT, "Profile Status");
        }
        return profileStatus;
    }

    public SettingsTab closeProfile() {
        if (isPlatformIos(driver)) {
            clickElemBy(driver, IOS_CLOSE_PROFILE_BTN, "Close profile button");
        }
        if (isPlatformAndroid(driver)) {
            clickElemBy(driver, ANDROID_CLOSE_PROFILE_BTN, "Close profile button");
        }
        return this;
    }
}
