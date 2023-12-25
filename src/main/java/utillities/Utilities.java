package utillities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Objects;

public class Utilities {
    private Utilities(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean isPlatformAndroid(AppiumDriver<MobileElement> driver) {
        return Objects.requireNonNull(driver.getPlatformName()).equalsIgnoreCase("Android");
    }

    public static boolean isPlatformIos(AppiumDriver<MobileElement> driver) {
        return Objects.requireNonNull(driver.getPlatformName()).equalsIgnoreCase("IOS");
    }
}