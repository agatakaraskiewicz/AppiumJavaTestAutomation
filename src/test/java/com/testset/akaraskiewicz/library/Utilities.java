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
        service = new AppiumServiceBuilder().withAppiumJS(new File("//usr//local//lib//node_modules//appium//build//lib//main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        //start the service
        service.start();

        //UIAutomatior options
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("FirstEmulator");
        options.setApp("//Users//AgataKaraskiewicz//GitProjects//AppiumJavaTestAutomation//src//test//java//resources//ApiDemos-debug.apk");

        //object for AndroidDriver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

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

    @AfterMethod
    public void tearDown() {
        driver.quit();
        //stop the service
        service.stop();
    }
}
