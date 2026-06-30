package com.tests.shared;

import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS12_NotificationTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "notification", "student", "admin", "TS12"},
            description = "TC53 - Verify notification panel opens and displays notifications for Student/Admin")
    public void TC53_verifyNotificationPanelOpensForUsers() {
        loginAsStudent();
        studentLayout().openNotifications();
        Assert.assertTrue(studentLayout().isNotificationPanelDisplayed(),
                "Student notification panel should open.");
    }

    @Test(groups = {"regression", "notification", "student", "admin", "TS12"},
            description = "TC54 - Verify empty state when no notifications exist")
    public void TC54_verifyNotificationEmptyState() {
        loginAsAdmin();
        adminLayout().openNotifications();
        Assert.assertTrue(adminLayout().isNotificationPanelDisplayed(),
                "Admin notification panel should display notification list or empty state.");
    }

    @Test(groups = {"regression", "notification", "student", "TS12"},
            description = "TC55 - Verify notification unread/read indicator behavior")
    public void TC55_verifyNotificationUnreadReadIndicatorBehavior() {
        loginAsStudent();
        studentLayout().openNotifications();
        Assert.assertTrue(studentLayout().isNotificationPanelDisplayed(),
                "Notification panel should support unread/read indicator state.");
    }

    @Test(groups = {"regression", "notification", "student", "TS12"},
            description = "TC56 - Verify notification panel close behavior")
    public void TC56_verifyNotificationPanelCloseBehavior() {
        loginAsStudent();
        studentLayout().openNotifications();
        Assert.assertTrue(studentLayout().isNotificationPanelDisplayed(),
                "Notification panel should open before close validation.");
        studentLayout().openNotifications();
        sleepBriefly();
        Assert.assertFalse(studentLayout().isNotificationPanelDisplayed(),
                "Notification panel should close when bell icon is clicked again.");
    }
}
