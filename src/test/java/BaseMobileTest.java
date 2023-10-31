
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;

public class BaseMobileTest {
    protected SoftAssert softAssert = new SoftAssert();
    protected SettingsTab settingsTab;
    protected BasePage basePage;

    public void pressHomeButton(AndroidDriver<MobileElement> androidDriver){
        androidDriver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

}
