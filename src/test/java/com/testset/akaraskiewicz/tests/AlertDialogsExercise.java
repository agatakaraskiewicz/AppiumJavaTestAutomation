package com.testset.akaraskiewicz.tests;

import com.testset.akaraskiewicz.library.Utilities;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlertDialogsExercise extends Utilities {

    public void alertDialogsSetup() {
        //go to the Alert Dialogs -> having these steps in @BeforeMethod crashes the app
        driver.findElement(AppiumBy.accessibilityId("App")).click();
        driver.findElement(AppiumBy.accessibilityId("Alert Dialogs")).click();
    }
    @Test (groups = "AlertDialogs")
    public void dialogWithAMessageTest() {
        alertDialogsSetup();

        driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a message")).click();

        //verify if the pop-up exists and check its content (icon, title and buttons)
        boolean isPopUpDisplayed = driver.findElement(By.id("android:id/parentPanel")).isDisplayed();
        Assert.assertTrue(isPopUpDisplayed);

        boolean isIconDisplayed = driver.findElement(By.id("android:id/icon")).isDisplayed();
        Assert.assertTrue(isIconDisplayed);

        String popUpTitleText = driver.findElement(By.id("android:id/alertTitle")).getText();
        Assert.assertEquals(popUpTitleText, "Lorem ipsum dolor sit aie consectetur adipiscing\n" +
                "Plloaso mako nuto siwuf cakso dodtos anr koop.");

        boolean isCancelBtnDisplayed = driver.findElement(By.xpath("//android.widget.Button[@text = 'Cancel']")).isDisplayed();
        Assert.assertTrue(isCancelBtnDisplayed);

        boolean isOkBtnDisplayed = driver.findElement(By.xpath("//android.widget.Button[@text = 'OK']")).isDisplayed();
        Assert.assertTrue(isOkBtnDisplayed);
    }
}
