
import com.github.javafaker.Faker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;

import java.time.Duration;

public class BaseMobileTest {
    protected SoftAssert softAssert = new SoftAssert();
    protected Faker faker = new Faker();
    protected SettingsTab settingsTab;
    protected BasePage basePage;

    @BeforeSuite
    public void setupPlatform() {

    }

    public void pressBackButton(AppiumDriver<MobileElement> driver) {
        driver.navigate().back();
    }

    //the below method is working for Android platform only
    public void lockDevice(AppiumDriver<MobileElement> driver) {
        ((AndroidDriver<MobileElement>) driver).lockDevice(Duration.ofSeconds(5));
    }

}
