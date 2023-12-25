
import org.testng.annotations.Test;

public class TestCheckProfileAndroid extends BaseMobileTest {

    @Test(enabled = true)
    public void testProfileAndroid() {
        String expectedStatus = (String) properties.get("android.profile.status");
        openApplication(androidDriver);
        settingsTab = androidBasePage.openSettingsTab(androidDriver);

        String actualProfileName = settingsTab.openProfile().getProfileName();

        String actualStatus = settingsTab.getStatus();

        softAssert.assertTrue(actualProfileName.equals(androidConsumerName),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualProfileName, androidConsumerName));

        softAssert.assertTrue(actualStatus.equals(expectedStatus),
                String.format("actual profile name is not equals to expected \n actual: %s, \n expected: %s",
                        actualStatus, expectedStatus));

        settingsTab.closeProfile();
        pressBackButton(androidDriver);
        softAssert.assertAll();
    }

}
