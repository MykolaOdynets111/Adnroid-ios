
import com.github.javafaker.Faker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
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


    @BeforeMethod
    public void setUpDrivers() {

        iOSDesiredCapabilities.setCapability("platformName", "ios");
        iOSDesiredCapabilities.setCapability("appium:automationName", "xcuitest");
        iOSDesiredCapabilities.setCapability("appium:udid", "00008020-001C51981A05002E");
        iOSDesiredCapabilities.setCapability("appium:bundleId", "staging.hubnub.io");
        iOSDesiredCapabilities.setCapability("appium:deviceName", "iPhone_Mykola_test");
        iOSDesiredCapabilities.setCapability("appium:xcodeOrgId", "9S9NSPYJKN");
        iOSDesiredCapabilities.setCapability("appium:xcodeSigningId", "Apple Developer");
        iOSDesiredCapabilities.setCapability("appium:showXcodeLog", true);
        iOSDesiredCapabilities.setCapability("appium:includeSafariInWebviews", true);
        iOSDesiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        iOSDesiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);


        androidDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        androidDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        androidDesiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_6_pro");
        androidDesiredCapabilities.setCapability("appium:applicationId", "io.shadow.chat.staging");
        androidDesiredCapabilities.setCapability("appium:appActivity", ".MainActivity");
        androidDesiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        androidDesiredCapabilities.setCapability("newCommandTimeout", 20000);

        try {
            URL iosUrl = new URL("http://127.0.0.1:4723");
            iOSDriver = new IOSDriver<>(iosUrl, iOSDesiredCapabilities);
            URL androidUrl = new URL("http://0.0.0.0:4733/");
            androidDriver = new AndroidDriver<>(androidUrl, androidDesiredCapabilities);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        iOSDriver.quit();
        androidDriver.quit();
    }
}
