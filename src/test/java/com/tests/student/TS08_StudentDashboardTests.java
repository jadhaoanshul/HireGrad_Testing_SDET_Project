package com.tests.student;

import com.framework.pages.HomePage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS08_StudentDashboardTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "student", "dashboard", "TS08"},
            description = "TC41 - Verify Student Dashboard UI elements are loaded")
    public void TC41_verifyStudentDashboardUiElementsAreLoaded() {
        loginAsStudent();
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isStudentDashboardLoaded(),
                "Student Dashboard page should be loaded.");
        Assert.assertTrue(homePage.areStudentDashboardCardsDisplayed(),
                "Student dashboard summary and profile/application sections should be displayed.");
    }

    @Test(groups = {"smoke", "regression", "student", "dashboard", "navigation", "TS08"},
            description = "TC42 - Verify Student Dashboard sidebar navigation routing")
    public void TC42_verifyStudentDashboardSidebarNavigationRouting() {
        loginAsStudent();

        studentLayout().goJobs();
        Assert.assertTrue(driver.getCurrentUrl().contains("/student/jobs"));
        studentLayout().goTracker();
        Assert.assertTrue(driver.getCurrentUrl().contains("/student/tracker"));
        studentLayout().goHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("/student/home"));
    }
}
