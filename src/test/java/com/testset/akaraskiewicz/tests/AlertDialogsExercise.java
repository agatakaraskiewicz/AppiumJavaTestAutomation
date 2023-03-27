package com.testset.akaraskiewicz.tests;

import com.testset.akaraskiewicz.library.Utilities;
import io.appium.java_client.AppiumBy;
import org.testng.annotations.Test;

public class AlertDialogsExercise extends Utilities {
    @Test (groups = "AlertDialogs")
    public void dialogWithAMessageTest() {
        driver.findElement(AppiumBy.accessibilityId("OK Cancel dialog with a message")).click();

    }
}
