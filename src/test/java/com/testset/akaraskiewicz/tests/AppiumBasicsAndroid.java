package com.testset.akaraskiewicz.tests;

import com.testset.akaraskiewicz.library.Utilities;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppiumBasicsAndroid extends Utilities {
    @Test
    public void provideWiFiSettingsName() {

        //click on Preference - generic xpath
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Preference']")).click();
        //Click on Preference dependencies - by appium accessibility ID
        driver.findElement(AppiumBy.accessibilityId("3. Preference dependencies")).click();
        //click on the checkbox - we could use //android.widget.CheckBox[contains(@resource-id, 'checkbox')], but no.
        driver.findElement(By.id("android:id/checkbox")).click();
        //click on WiFi settings - I don't like that xpath, but this app is... interestingly crafted
        driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
        //input WiFi name into the pop-up; firstly assert if the pop-up title is correct
        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(alertTitle, "WiFi settings");
        driver.findElement(By.id("android:id/edit")).sendKeys("Maguszyna");
        //confirm the changes - OK button (there is id, but just for exercise)
        driver.findElements(AppiumBy.className("android.widget.Button")).get(1).click();

    }

    @Test
    public void longPressTest() {
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
        //long press on an Element
        WebElement peopleNames = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));
        longPressAction(peopleNames);

        //assert if the menu after the long press action appeared
        Assert.assertTrue(driver.findElement(By.id("android:id/title")).isDisplayed());

        //assert if the menu after the long press action has a correct title
        String menuTitle = driver.findElement(By.id("android:id/title")).getText();
        Assert.assertEquals(menuTitle, "Sample menu");
    }

    @Test
    public void scrollDownTest() {
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Views']")).click();

        //UIAutomator tool to do that
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));"));
        Assert.assertTrue(driver.findElement(AppiumBy.accessibilityId("WebView")).isDisplayed());

        //sing Javascript Executor -> to scroll just a bit to see if something exists
        //scrollDownToTheEnd();
    }

    @Test
    public void swipeTest() {
        //go to the photos gallery
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();

        //create elements
        WebElement firstImage = driver.findElement(By.xpath("//android.widget.ImageView[1]"));
        WebElement secondImage = driver.findElement(By.xpath("//android.widget.ImageView[2]"));

        //check if the 1st image is focusable by default and 2nd is not
        Assert.assertTrue(Boolean.parseBoolean(firstImage.getAttribute("focusable")));
        Assert.assertFalse(Boolean.parseBoolean(secondImage.getAttribute("focusable")));

        //swipe so the 2nd image will be focusable
        swipe(firstImage, "left");

        //check if 1st image is not focusable anymore and 2nd one is
        Assert.assertFalse(Boolean.parseBoolean(firstImage.getAttribute("focusable")));
        Assert.assertTrue(Boolean.parseBoolean(secondImage.getAttribute("focusable")));
    }
}
