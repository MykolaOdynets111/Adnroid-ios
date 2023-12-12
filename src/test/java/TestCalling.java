
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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.ActiveCall;
import pages.BasePage;
import pages.CallsTab;
import pages.IncomingCall;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestCalling extends BaseMobileTest {
    private final DesiredCapabilities iOSDesiredCapabilities = new DesiredCapabilities();
    private final DesiredCapabilities androidDesiredCapabilities = new DesiredCapabilities();
    private AppiumDriver<MobileElement> iOSDriver;
    private AppiumDriver<MobileElement> androidDriver;
    private BasePage iOSBasePage;
//    private BasePage androidBasePage;

    @BeforeSuite
    public void setupDeviceCapabilities() {

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
        androidDesiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_6_Test");
        androidDesiredCapabilities.setCapability("appium:applicationId", "io.shadow.chat.staging");
        androidDesiredCapabilities.setCapability("appium:appActivity", ".MainActivity");
        androidDesiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        androidDesiredCapabilities.setCapability("newCommandTimeout", 20000);
    }

    @BeforeMethod
    public void spinUpDrivers() {
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
//        androidBasePage = new BasePage(androidDriver);
    }

    @Test(enabled = true)
    public void testCallIosToAndroid() {

        CallsTab iOSCallsTab = iOSBasePage.openCallsTab(iOSDriver);
        iOSCallsTab.makeCallByName("Mykola_Test_Kyivstar", iOSDriver);
        lockDevice(androidDriver);

        IncomingCall androidIncomingCall = new IncomingCall(androidDriver);
        softAssert.assertTrue(androidIncomingCall.isCallRequested(androidDriver), "The incoming call does not occur");

        ActiveCall androidActiveCall = androidIncomingCall.answerCall(androidDriver);
        softAssert.assertTrue(androidActiveCall.isCallInProgress(androidDriver), "The call does not start");
        androidActiveCall.stopCall(androidDriver);

        softAssert.assertAll();
    }

    @AfterMethod
    public void closeDrivers() {
        pressBackButton(androidDriver);
        pressBackButton(iOSDriver);
        iOSDriver.quit();
        androidDriver.quit();
    }
}
