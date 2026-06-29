package admin;

import base.AdminBaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.admin.AdminDashboardPage;

@Listeners(TestListener.class)
public class AdminDashboardTest extends AdminBaseTest {

    private AdminDashboardPage adminDashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void openAdminDashboard() {
        navigateToAdminDashboardPage();
        adminDashboardPage = new AdminDashboardPage(driver);
        Assert.assertTrue(
                adminDashboardPage.isAdminDashboardPageLoaded(),
                "Admin dashboard should be loaded on /admin/home route");
    }

    @Test(
            priority = 1,
            description = "TS03_TC14: Verify all Admin Dashboard UI components are visible in proper format")
    public void TC14_verifyAdminDashboardUIComponentsAreVisible() {
        Assert.assertTrue(
                adminDashboardPage.isAdminDashboardHeadingDisplayed(),
                "Admin dashboard heading should be displayed on top");

        Assert.assertTrue(
                adminDashboardPage.arePlacementSummaryCardsDisplayed(),
                "Placement summary cards should display Active postings, Total applicants, Selected and Pending reviews");

        Assert.assertTrue(
                adminDashboardPage.arePostingOverviewDetailsDisplayed(),
                "Posting overview, Selection rate and Recent activity sections should be displayed");
    }

    @Test(
            priority = 2,
            description = "TS03_TC16: Verify dashboard handles loading/error/content state")
    public void TC16_verifyDashboardDisplaysContentOrLoadingOrErrorState() {
        Assert.assertTrue(
                adminDashboardPage.isDashboardContentOrStatusMessageDisplayed(),
                "Dashboard should show summary content, loading state, or a valid error message");
    }

    @Test(
            priority = 3,
            description = "TS03_TC15: Verify Admin Dashboard sidebar links navigate to expected pages")
    public void TC15_verifyAdminDashboardSidebarNavigationLinks() {
        adminDashboardPage.clickJobPostingLink();
        Assert.assertTrue(
                adminDashboardPage.isJobPostingPageRouteDisplayed(),
                "Job Posting sidebar link should navigate to /admin/jobs");

        navigateToAdminDashboardPage();
        adminDashboardPage = new AdminDashboardPage(driver);
        adminDashboardPage.clickApplicationManagementLink();
        Assert.assertTrue(
                adminDashboardPage.isApplicationManagementPageRouteDisplayed(),
                "Application Management sidebar link should navigate to /admin/applications");

        navigateToAdminDashboardPage();
        adminDashboardPage = new AdminDashboardPage(driver);
        adminDashboardPage.clickReportsLink();
        Assert.assertTrue(
                adminDashboardPage.isReportsPageRouteDisplayed(),
                "Reports sidebar link should navigate to /admin/reports");

        navigateToAdminDashboardPage();
        adminDashboardPage = new AdminDashboardPage(driver);
        adminDashboardPage.clickProfile();
        adminDashboardPage.clickProfileLink();
        Assert.assertTrue(
                adminDashboardPage.isProfilePageRouteDisplayed(),
                "Profile sidebar link should navigate to Admin Profile page");

        //adminDashboardPage.clickProfile();
        adminDashboardPage.clickLogoutButton();
        Assert.assertTrue(
                adminDashboardPage.isLoginPageRouteDisplayed(),
                "Logout should redirect admin to Login page");
    }
}
