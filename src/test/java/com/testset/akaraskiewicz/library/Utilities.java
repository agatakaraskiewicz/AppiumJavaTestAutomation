package com.testset.akaraskiewicz.library;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Utilities {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;

    @BeforeMethod
    public void appiumConfig() throws MalformedURLException {
        //define Appium service
        service = new AppiumServiceBuilder().withAppiumJS(new File(Constants.APPIUM_PATH))
                .withIPAddress(Constants.APPIUM_SERVER_ADDRESS)
                .usingPort(Constants.APPIUM_SERVER_PORT)
                .build();
        //start the service
        service.start();

        //UIAutomator options
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(Constants.ANDROID_EMULATOR_NAME);
        options.setApp(Constants.APP_PATH);

        //object for AndroidDriver
        driver = new AndroidDriver(new URL(Constants.ANDROID_DRIVER_URL), options);

        //implicit wait... for now.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void longPressAction(WebElement elementToBeLongPressed) {
        ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of(
                        "elementId", ((RemoteWebElement)elementToBeLongPressed).getId(),
                        "duration", 2000));
    }

    public void scrollDownToTheEnd() throws InterruptedException {
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", "down",
                    "percent", 10.0
            ));
            //sleep is here due to some issues with canScrollMore showing the actual true value and the scroll breaks before it reaches bottom
            Thread.sleep(1000);
        } while (canScrollMore);
    }

    public void swipe(WebElement elementToStartWith, String whichTurn) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement)elementToStartWith).getId(),
                "direction", whichTurn,
                "percent", 0.75
        ));
    }

    public void dragNDrop(WebElement draggedElement, int endX, int endY) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) draggedElement).getId(),
                "endX", endX,
                "endY", endY
        ));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        //stop the service
        service.stop();
    }
}
