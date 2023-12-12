
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
import pages.ChatWindow;
import pages.ChatsTab;

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

        pressBackButton(androidDriver);
        androidDriver.findElementByXPath("//android.widget.TextView[@content-desc='Staging Hubnub']").click();

        iOSBasePage = new BasePage(iOSDriver);
        androidBasePage = new BasePage(androidDriver);

    }

    @Test(enabled = true)
    public void testSendMessage() {

        String messageFromAndroidToIos = faker.name().firstName();

        ChatsTab androidChatsTab = androidBasePage.openChatsTab(androidDriver);
        ChatWindow androidChat = androidChatsTab.openChatByName("Mykola_Test_Lifecell", androidDriver);
        androidChat.sendMessage(messageFromAndroidToIos, androidDriver);

        ChatsTab iOSChatsTab = iOSBasePage.openChatsTab(iOSDriver);
        ChatWindow iOSChat = iOSChatsTab.openChatByName("Mykola_Test_Kyivstar", iOSDriver);
        softAssert.assertTrue(iOSChat.isChatContainsMessage(messageFromAndroidToIos, iOSDriver),
                "The message from Android to IOS was not sent");

        String messageFromIosToAndroid = faker.name().lastName();
        iOSChat.sendMessage(messageFromIosToAndroid, iOSDriver);
        softAssert.assertTrue(androidChat.isChatContainsMessage(messageFromIosToAndroid, androidDriver),
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
