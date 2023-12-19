
import org.testng.annotations.Test;


public class TestCheckProfileIos extends BaseMobileTest {
    private final static String EXPECTED_PROFILE_NAME = "Mykola_Test_Lifecell";
    private final static String EXPECTED_STATUS = "Hi there, I'm using HubNub!";

    @Test(enabled = true)
    public void testLoginIos() {
        settingsTab = iOSBasePage.openSettingsTab(iOSDriver);
        String actualProfileName = settingsTab.openProfile().getProfileName();
        String actualStatus = settingsTab.getStatus();

        softAssert.assertTrue(actualProfileName.equals(EXPECTED_PROFILE_NAME),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, EXPECTED_PROFILE_NAME));

        softAssert.assertTrue(actualStatus.equals(EXPECTED_STATUS),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, EXPECTED_STATUS));

        settingsTab.closeProfile();
        iOSBasePage.openChatsTab(iOSDriver);
        pressBackButton(iOSDriver);
        softAssert.assertAll();
    }

}
