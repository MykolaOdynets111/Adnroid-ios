
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import pages.BasePage;
import pages.SettingsTab;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseMobileTest {
    protected SoftAssert softAssert = new SoftAssert();
    protected SettingsTab settingsTab;
    protected BasePage basePage;


    String configFilePath = "src/main/resources/config.properties";
    protected Properties prop;
    String platform;

    @BeforeSuite
    public void setupPlatform() throws IOException {
        FileInputStream propsInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propsInput);
        platform = prop.getProperty("platform");
    }

    public void pressHomeButton(AppiumDriver<MobileElement> driver) {
        driver.navigate().back();
    }

}
