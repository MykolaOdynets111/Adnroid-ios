
import org.testng.annotations.Test;
import pages.ActiveCall;
import pages.CallsTab;
import pages.IncomingCall;

public class TestCalling extends BaseMobileTest {

    @Test(enabled = true)
    public void testCallIosToAndroid() throws InterruptedException {
        openApplication(androidDriver);
        openApplication(iOSDriver);
        CallsTab iOSCallsTab = iOSBasePage.openCallsTab(iOSDriver);
        iOSCallsTab.makeCallByName(androidConsumerName, iOSDriver);
        lockDevice(androidDriver);

        IncomingCall androidIncomingCall = new IncomingCall(androidDriver);
        softAssert.assertTrue(androidIncomingCall.isCallRequested(androidDriver, iosConsumerName), "The incoming call does not occur.");

        ActiveCall androidActiveCall = androidIncomingCall.answerCall(androidDriver);
        softAssert.assertTrue(androidActiveCall.isCallInProgress(androidDriver, iosConsumerName), "The call does not start.");
        androidActiveCall.stopCall(androidDriver);

        softAssert.assertAll();
    }
    @Test(enabled = true)
    public void testCallAndroidToIOS() throws InterruptedException {
        openApplication(androidDriver);
        openApplication(iOSDriver);

        CallsTab androidCallsTab = androidBasePage.openCallsTab(androidDriver);
        androidCallsTab.makeCallByName(iosConsumerName, androidDriver);
        IncomingCall iosIncomingCall = new IncomingCall(iOSDriver);
        softAssert.assertTrue(iosIncomingCall.isCallRequested(iOSDriver, androidConsumerName), "The incoming call does not show");

        ActiveCall iosActiveCall = iosIncomingCall.answerCall(iOSDriver);
        softAssert.assertTrue(iosActiveCall.isCallInProgress(iOSDriver, androidConsumerName), "The call does not start");

        iosActiveCall.stopCall(iOSDriver);
        softAssert.assertAll();
    }
}
