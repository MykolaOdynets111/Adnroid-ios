
import com.github.javafaker.Faker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseMobileTest {
    protected SoftAssert softAssert = new SoftAssert();
    protected Faker faker = new Faker();
    protected SettingsTab settingsTab;

    protected final DesiredCapabilities iOSDesiredCapabilities = new DesiredCapabilities();
    protected final DesiredCapabilities androidDesiredCapabilities = new DesiredCapabilities();
    protected AppiumDriver<MobileElement> iOSDriver;
    protected AppiumDriver<MobileElement> androidDriver;
    protected BasePage iOSBasePage;
    protected BasePage androidBasePage;
    protected String iosConsumerName;
    protected String androidConsumerName;
    Properties properties = new Properties();

    @BeforeMethod
    public void setUpDrivers() throws IOException {
        properties.load(new FileInputStream("src/main/resources/config.properties"));

        iOSDesiredCapabilities.setCapability("platformName", properties.get("ios.platform.name"));
        iOSDesiredCapabilities.setCapability("appium:automationName", properties.get("ios.automation.name"));
        iOSDesiredCapabilities.setCapability("appium:udid", properties.get("ios.phone.udid"));
        iOSDesiredCapabilities.setCapability("appium:bundleId", properties.get("ios.bundle.id"));
        iOSDesiredCapabilities.setCapability("appium:deviceName", properties.get("ios.device.name"));
        iOSDesiredCapabilities.setCapability("appium:xcodeOrgId", properties.get("ios.xcode.org.id"));
        iOSDesiredCapabilities.setCapability("appium:xcodeSigningId", properties.get("ios.xcode.signing.id"));
        iOSDesiredCapabilities.setCapability("appium:newCommandTimeout", properties.get("ios.new.command.timeout"));
        iOSDesiredCapabilities.setCapability("appium:connectHardwareKeyboard", properties.get("ios.connect.hardware.keyboard"));

        androidDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.get("android.platform.name"));
        androidDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.get("android.platform.version"));
        androidDesiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.get("android.device.name"));
        androidDesiredCapabilities.setCapability("appium:applicationId", properties.get("android.application.id"));
        androidDesiredCapabilities.setCapability("appium:appActivity", properties.get("android.application.activity"));
        androidDesiredCapabilities.setCapability("appium:automationName", properties.get("android.automation.name"));
        androidDesiredCapabilities.setCapability("newCommandTimeout", properties.get("android.new.command.timeout"));

        try {
            URL iosUrl = new URL((String) properties.get("ios.url"));
            iOSDriver = new IOSDriver<>(iosUrl, iOSDesiredCapabilities);
            URL androidUrl = new URL((String) properties.get("android.url"));
            androidDriver = new AndroidDriver<>(androidUrl, androidDesiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        iosConsumerName = (String) properties.get("ios.consumer.name");
        androidConsumerName = (String) properties.get("android.consumer.name");
        iOSDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ((AndroidDriver<?>) androidDriver).pressKey(new KeyEvent(AndroidKey.HOME));
        androidDriver.findElementByXPath("//android.widget.TextView[@content-desc='Staging Hubnub']").click();
        iOSBasePage = new BasePage(iOSDriver);
        androidBasePage = new BasePage(androidDriver);
    }

    public void pressBackButton(AppiumDriver<MobileElement> driver) {
        driver.navigate().back();
    }

    //the below method is working for Android platform only
    public void lockDevice(AppiumDriver<MobileElement> driver) {
        ((AndroidDriver<MobileElement>) driver).lockDevice(Duration.ofSeconds(5));
    }
    @AfterMethod
    public void closeDrivers() {
        pressBackButton(androidDriver);
        pressBackButton(iOSDriver);
        ((AndroidDriver<?>) androidDriver).pressKey(new KeyEvent(AndroidKey.HOME));
        iOSDriver.quit();
        androidDriver.quit();
    }
}
