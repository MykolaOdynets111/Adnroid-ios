
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.BasePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestLoginIos extends BaseMobileTest {
    private final static String EXPECTED_PROFILE_NAME = "Mykola_Test_Lifecell";
    private final static String EXPECTED_STATUS = "Hi there, I'm using HubNub!";

    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    private AppiumDriver<MobileElement> driver;

    @BeforeSuite
    public void setupDeviceCapabilities() {

        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("appium:automationName", "xcuitest");
        desiredCapabilities.setCapability("appium:udid", "00008020-001C51981A05002E");
        desiredCapabilities.setCapability("appium:bundleId", "staging.hubnub.io");
        desiredCapabilities.setCapability("appium:deviceName", "iPhone_Mykola_test");
        desiredCapabilities.setCapability("appium:xcodeOrgId", "9S9NSPYJKN");
        desiredCapabilities.setCapability("appium:xcodeSigningId", "Apple Developer");
        desiredCapabilities.setCapability("appium:showXcodeLog", true);
        desiredCapabilities.setCapability("appium:includeSafariInWebviews", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
    }


    @BeforeMethod
    public void spinUpIosDriver() {
        try {
            URL url = new URL("http://127.0.0.1:4723");
            driver = new IOSDriver<>(url, desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        basePage = new BasePage(driver);
    }


    @Test(enabled = true)
    public void testLoginIos() {
        platform = "ios";
        settingsTab = basePage.openSettingsTab(driver, platform);
        String actualProfileName = settingsTab.openProfile(platform).getProfileName(platform);
        String actualStatus = settingsTab.getStatus(platform);

        softAssert.assertTrue(actualProfileName.equals(EXPECTED_PROFILE_NAME),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, EXPECTED_PROFILE_NAME));

        softAssert.assertTrue(actualStatus.equals(EXPECTED_STATUS),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, EXPECTED_STATUS));

        settingsTab.closeProfile(platform);
        basePage.openChatsTab(driver, platform);
        pressBackButton(driver);
        softAssert.assertAll();
    }

    @AfterMethod
    public void closeDriver() {
        driver.quit();
    }
}
