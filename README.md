This framework is using next technologies:

Java - main programming language

Test NG as main testing tool

Appium driver for running Android and IOS emulators.

X-Code for running on the real iPhone devices

Running the hubnud application on the iPhone (real device) at the local masshine
1. Turn on the “Developer mode on iPhone
Settings >Privacy & Security > Developer Mode, Press “Restart”
2. Install VPN on iPhone, connect it to the PC via cabel
3. Sign-in with AppleId on iPhone
4. Install TestFlight on the iPhone
5. Upload Hubnus Application using TestFlight
6. Change the Installed Hubnub with needed testing version
7. Sign-in using OTP
8. Install X-Code
9. Run WebDriverAgent
10. Try to see the list of devices, and store the ID of physical device:

xcrun xctrace list devices

11. Switch to appium-webdriveragent directory in the Appium:

cd /Users/mykolaodynets/.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent
12. run WebDriverAgent at the physical device (add id of device, stored in the step 10):

xcodebuild -allowProvisioningUpdates -project WebDriverAgent.xcodeproj -scheme WebDriverAgentRunner -destination 'id=00008020-001C51981A05002E' test

13. set up tests and run them on the Iphone


Run tests:
Before the tests are running we need to connect physical iPhone device to the laptop and run the Android emulator
1. open the new terminal window
2. go to appium-webdriveragent repo:
cd /Users/mykolaodynets/.appium/node_modules/appium-xcuitest-driver/node_modules/appium-webdriveragent
3. run  WebDriverAgent for appropriate device (00008020-001C51981A05002E):
xcodebuild -allowProvisioningUpdates -project WebDriverAgent.xcodeproj -scheme WebDriverAgentRunner -destination 'id=00008020-001C51981A05002E' test
4. open the new terminal tab and run the Appium server:
appium
5. open the new terminal tab and go to autotests repo:
cd /Users/mykolaodynets/Documents/dubai/untitled
6. run tests:
mvn clean verify
