
import org.testng.annotations.Test;
import pages.ChatWindow;
import pages.ChatsTab;

public class TestMessaging extends BaseMobileTest {

    @Test(enabled = true)
    public void testSendMessage() {

        String messageFromAndroidToIos = faker.name().firstName();

        ChatsTab androidChatsTab = androidBasePage.openChatsTab(androidDriver);
        ChatWindow androidChat = androidChatsTab.openChatByName(iosConsumerName, androidDriver);
        androidChat.sendMessage(messageFromAndroidToIos, androidDriver);

        ChatsTab iOSChatsTab = iOSBasePage.openChatsTab(iOSDriver);
        ChatWindow iOSChat = iOSChatsTab.openChatByName(androidConsumerName, iOSDriver);
        softAssert.assertTrue(iOSChat.isChatContainsMessage(messageFromAndroidToIos, iOSDriver),
                "The message from Android to IOS was not sent");

        String messageFromIosToAndroid = faker.name().lastName();
        iOSChat.sendMessage(messageFromIosToAndroid, iOSDriver);
        softAssert.assertTrue(androidChat.isChatContainsMessage(messageFromIosToAndroid, androidDriver),
                "The message from IOS to Android was not sent");

        softAssert.assertAll();
    }
}
