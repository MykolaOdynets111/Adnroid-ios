
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.ChatTab;
import pages.ChatWindow;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestMessaging extends BaseMobileTest {
    private final DesiredCapabilities iOSDesiredCapabilities = new DesiredCapabilities();
    private final DesiredCapabilities androidDesiredCapabilities = new DesiredCapabilities();
    private AppiumDriver<MobileElement> iOSDriver;
    private AppiumDriver<MobileElement> androidDriver;
    private BasePage iOSBasePage;
    private BasePage androidBasePage;

    @BeforeSuite
    public void setupDeviceCapabilities() {

        iOSDesiredCapabilities.setCapability("platformName", "iOS");
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

        androidDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
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
            URL androidUrl = new URL("http://127.0.0.1:4733/wd/hub");
            androidDriver = new AndroidDriver<>(androidUrl, androidDesiredCapabilities);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        iOSDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        pressBackButton(androidDriver);
        androidDriver.findElementByXPath("//android.widget.TextView[@content-desc='Staging Hubnub']").click();

        iOSBasePage = new BasePage(iOSDriver);
        androidBasePage = new BasePage(androidDriver);

    }

    @Test(enabled = true)
    public void testSendMessage() throws InterruptedException {
        String iOSPlatform = "ios";
        String androidPlatform = "android";
        String messageFromAndroidToIos = faker.name().firstName();

        ChatTab androidChatTab = androidBasePage.openChatsTab(androidDriver, androidPlatform);
        ChatWindow androidChat = androidChatTab.openChatByName("Mykola_Test_Lifecell", androidPlatform, androidDriver);
        androidChat.sendMessage(messageFromAndroidToIos, androidPlatform, androidDriver);

        ChatTab iOSChatTab = iOSBasePage.openChatsTab(iOSDriver, iOSPlatform);
        Thread.sleep(2000);
        ChatWindow iOSChat = iOSChatTab.openChatByName("Mykola_Test_Kyivstar", iOSPlatform, iOSDriver);
        softAssert.assertTrue(iOSChat.isChatContainsMessage(messageFromAndroidToIos, iOSPlatform, iOSDriver),
                "The message from Android to IOS was not sent");

        String messageFromIosToAndroid = faker.name().lastName();
        iOSChat.sendMessage(messageFromIosToAndroid, iOSPlatform, iOSDriver);
        softAssert.assertTrue(androidChat.isChatContainsMessage(messageFromIosToAndroid, androidPlatform, androidDriver),
                "The message from IOS to Android was not sent");

        softAssert.assertAll();
    }

    @AfterMethod
    public void closeDrivers() {
        pressBackButton(androidDriver);
        pressBackButton(androidDriver);
        iOSDriver.quit();
        androidDriver.quit();
    }
}
