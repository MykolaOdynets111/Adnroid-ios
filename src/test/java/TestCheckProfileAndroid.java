
import org.testng.annotations.Test;

public class TestCheckProfileAndroid extends BaseMobileTest {
    private final static String EXPECTED_PROFILE_NAME = "Mykola_Test_Kyivstar";
    private final static String EXPECTED_STATUS = "TestStatus";

    @Test(enabled = true)
    public void testLoginAndroid() {
        pressBackButton(androidDriver);
        androidDriver.findElementByXPath("//android.widget.TextView[@content-desc='Staging Hubnub']").click();
        settingsTab = androidBasePage.openSettingsTab(androidDriver);

        String actualProfileName = settingsTab.openProfile().getProfileName();

        String actualStatus = settingsTab.getStatus();

        softAssert.assertTrue(actualProfileName.equals(EXPECTED_PROFILE_NAME),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, EXPECTED_PROFILE_NAME));

        softAssert.assertTrue(actualStatus.equals(EXPECTED_STATUS),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, EXPECTED_STATUS));

        settingsTab.closeProfile();
        androidBasePage.openChatsTab(androidDriver);
        pressBackButton(androidDriver);
        softAssert.assertAll();
    }

}
