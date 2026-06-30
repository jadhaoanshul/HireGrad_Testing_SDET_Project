package com.tests.admin;

import com.framework.pages.HomePage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS03_AdminDashboardTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "admin", "dashboard", "TS03"},
            description = "TC14 - Verify Admin Dashboard UI components")
    public void TC14_verifyAdminDashboardUiComponents() {
        loginAsAdmin();
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isAdminDashboardLoaded(), "Admin Dashboard should be loaded.");
        Assert.assertTrue(homePage.areAdminDashboardCardsDisplayed(),
                "Admin Dashboard summary cards and key sections should be displayed.");
    }

    @Test(groups = {"smoke", "regression", "admin", "dashboard", "navigation", "TS03"},
            description = "TC15 - Verify Admin Dashboard sidebar navigation links")
    public void TC15_verifyAdminDashboardSidebarNavigationLinks() {
        loginAsAdmin();

        adminLayout().goJobs();
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/jobs"));
        adminLayout().goApplications();
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/applications"));
        adminLayout().goReports();
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/reports"));
        adminLayout().goHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/home"));
    }

    @Test(groups = {"regression", "admin", "dashboard", "TS03"},
            description = "TC16 - Validate Admin Dashboard handles dashboard data status")
    public void TC16_validateAdminDashboardDataStatus() {
        loginAsAdmin();
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isAdminDashboardLoaded(),
                "Dashboard should show content and should not crash while data is loaded.");
    }
}
