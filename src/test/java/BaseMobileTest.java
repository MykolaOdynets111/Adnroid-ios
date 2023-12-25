
import com.github.javafaker.Faker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;
import utillities.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseMobileTest {
    Properties properties = new Properties();

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

    private URL iosUrl;
    private URL androidUrl;

    @BeforeMethod
    public void setUpCapabilities() throws IOException {
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
            iosUrl = new URL((String) properties.get("ios.url"));
            androidUrl = new URL((String) properties.get("android.url"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        iosConsumerName = (String) properties.get("ios.consumer.name");
        androidConsumerName = (String) properties.get("android.consumer.name");

    }

    @BeforeMethod
    public void setUpDrivers() {
        androidDriver = new AndroidDriver<>(androidUrl, androidDesiredCapabilities);
        androidDriver.terminateApp((String) properties.get("android.application.id"));
        iOSDriver = new IOSDriver<>(iosUrl, iOSDesiredCapabilities);
        WebDriverWait wait = new WebDriverWait(iOSDriver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name='Connected']")));
    }

    public void pressBackButton(AppiumDriver<MobileElement> driver) {
        driver.navigate().back();
    }

    //the below method is working for Android platform only
    public void lockDevice(AppiumDriver<MobileElement> driver) {
        ((AndroidDriver<MobileElement>) driver).lockDevice(Duration.ofSeconds(5));
    }

    public void openApplication(AppiumDriver<MobileElement> driver) {

        if (Utilities.isPlatformAndroid(driver)) {
            WebDriverWait wait = new WebDriverWait(driver, 50);

            androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.activateApp((String) properties.get("android.application.id"));
            //wait for application is being connected
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Connected']")));
            androidBasePage = new BasePage(driver);
        }
        if (Utilities.isPlatformIos(driver)) {
            iOSDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            iOSBasePage = new BasePage(driver);
        }
    }

    @AfterMethod
    public void closeDrivers() {
        pressBackButton(iOSDriver);
        iOSDriver.quit();
        androidDriver.terminateApp((String) properties.get("android.application.id"));
        androidDriver.quit();
    }
}
