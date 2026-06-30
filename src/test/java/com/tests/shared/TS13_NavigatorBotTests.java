package com.tests.shared;

import com.framework.pages.NavigatorBotPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS13_NavigatorBotTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "navigator", "student", "TS13"},
            description = "TC57 - Verify navigator bot opens from floating button")
    public void TC57_verifyNavigatorBotOpensFromFloatingButton() {
        loginAsStudent();
        NavigatorBotPage botPage = new NavigatorBotPage(driver);

        botPage.open();
        Assert.assertTrue(botPage.isOpen(), "Navigator bot panel should open.");
        botPage.close();
        Assert.assertFalse(botPage.isOpen(), "Navigator bot panel should close.");
    }

    @Test(groups = {"regression", "navigator", "student", "TS13"},
            description = "TC58 - Verify navigator bot displays shortcuts based on logged-in user role")
    public void TC58_verifyNavigatorBotDisplaysStudentShortcuts() {
        loginAsStudent();
        NavigatorBotPage botPage = new NavigatorBotPage(driver);

        botPage.open();
        Assert.assertTrue(botPage.hasShortcutChips(),
                "Navigator bot should show role-based shortcuts for Student.");
    }

    @Test(groups = {"regression", "navigator", "admin", "TS13"},
            description = "TC60 - Verify Admin user can view Admin-related navigation options")
    public void TC60_verifyAdminNavigatorBotOptions() {
        loginAsAdmin();
        NavigatorBotPage botPage = new NavigatorBotPage(driver);

        botPage.open();
        Assert.assertTrue(botPage.hasShortcutChips(),
                "Navigator bot should show role-based shortcuts for Admin.");
    }
}
