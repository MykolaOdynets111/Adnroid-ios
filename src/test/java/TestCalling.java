
import org.testng.annotations.Test;
import pages.ActiveCall;
import pages.CallsTab;
import pages.IncomingCall;

public class TestCalling extends BaseMobileTest {

    @Test(enabled = true) //working fine
    public void testCallIosToAndroid() throws InterruptedException {

        CallsTab iOSCallsTab = iOSBasePage.openCallsTab(iOSDriver);
        iOSCallsTab.makeCallByName("Mykola_Test_Kyivstar", iOSDriver);
        lockDevice(androidDriver);

        IncomingCall androidIncomingCall = new IncomingCall(androidDriver);
        softAssert.assertTrue(androidIncomingCall.isCallRequested(androidDriver, "Mykola_Test_Lifecell"), "The incoming call does not occur");

        ActiveCall androidActiveCall = androidIncomingCall.answerCall(androidDriver);
        softAssert.assertTrue(androidActiveCall.isCallInProgress(androidDriver, "Mykola_Test_Lifecell"), "The call does not start");
        androidActiveCall.stopCall(androidDriver);

        softAssert.assertAll();
    }
    @Test(enabled = true)
    public void testCallAndroidToIOS() throws InterruptedException {
        CallsTab androidCallsTab = androidBasePage.openCallsTab(androidDriver);
        androidCallsTab.makeCallByName("Mykola_Test_Lifecell", androidDriver);
        IncomingCall iosIncomingCall = new IncomingCall(iOSDriver);
        softAssert.assertTrue(iosIncomingCall.isCallRequested(iOSDriver, "Mykola_Test_Kyivstar"), "The incoming call does not show");

        ActiveCall iosActiveCall = iosIncomingCall.answerCall(iOSDriver);
        softAssert.assertTrue(iosActiveCall.isCallInProgress(iOSDriver, "Mykola_Test_Kyivstar"), "The call does not start");


        iosActiveCall.stopCall(iOSDriver);
        softAssert.assertAll();
    }
}
