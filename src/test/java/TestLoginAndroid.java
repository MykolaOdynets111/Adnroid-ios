
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.BasePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestLoginAndroid extends BaseMobileTest {
    private final static String EXPECTED_PROFILE_NAME = "Mykola_Test_Kyivstar";
    private final static String EXPECTED_STATUS = "TestStatus";

    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    private AndroidDriver<MobileElement> androidDriver;

    @BeforeSuite
    public void setupDeviceCapabilities() {
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_6_Test");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("newCommandTimeout", 20000);
    }

    @BeforeMethod
    public void spinUpAndroidDriver() {
        try {
            URL url = new URL("http://0.0.0.0:4733/");
            androidDriver = new AndroidDriver<>(url, desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        basePage = new BasePage(androidDriver);
    }


    @Test(enabled = true)
    public void testLoginAndroid() {
        pressBackButton(androidDriver);
        androidDriver.findElementByXPath("//android.widget.TextView[@content-desc='Staging Hubnub']").click();
        settingsTab = basePage.openSettingsTab(androidDriver);

        String actualProfileName = settingsTab.openProfile().getProfileName();

        String actualStatus = settingsTab.getStatus();

        softAssert.assertTrue(actualProfileName.equals(EXPECTED_PROFILE_NAME),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, EXPECTED_PROFILE_NAME));

        softAssert.assertTrue(actualStatus.equals(EXPECTED_STATUS),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, EXPECTED_STATUS));

        settingsTab.closeProfile();
        basePage.openChatsTab(androidDriver);
        pressBackButton(androidDriver);
        softAssert.assertAll();
    }

    @AfterMethod
    public void closeAndroidDriver() {
        androidDriver.quit();
    }
}
