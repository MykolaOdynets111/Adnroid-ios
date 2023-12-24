import org.testng.annotations.Test;


public class TestCheckProfileIos extends BaseMobileTest {

    @Test(enabled = true)
    public void testProfileIos() {
        String expectedStatus = (String) properties.get("ios.profile.status");
        settingsTab = iOSBasePage.openSettingsTab(iOSDriver);
        String actualProfileName = settingsTab.openProfile().getProfileName();
        String actualStatus = settingsTab.getStatus();

        softAssert.assertTrue(actualProfileName.equals(iosConsumerName),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, iosConsumerName));

        softAssert.assertTrue(actualStatus.equals(expectedStatus),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, expectedStatus));

        settingsTab.closeProfile();
        iOSBasePage.openChatsTab(iOSDriver);
        pressBackButton(iOSDriver);
        softAssert.assertAll();
    }
}
