package com.tests.shared;

import com.framework.pages.ThemePage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS14_ThemeTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "theme", "TS14"},
            description = "TC61 - Verify user can switch between light and dark mode")
    public void TC61_verifyUserCanSwitchBetweenLightAndDarkMode() {
        ThemePage themePage = new ThemePage(driver);
        String before = themePage.currentHtmlClass();

        themePage.toggleTheme();
        sleepBriefly();
        String after = themePage.currentHtmlClass();

        Assert.assertNotEquals(after, before,
                "Theme class should change after clicking theme toggle.");
    }
}
