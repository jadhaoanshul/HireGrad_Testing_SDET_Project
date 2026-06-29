package base;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import pages.admin.AdminDashboardPage;
import pages.LoginPage;
import util.PropertyFile;

import java.time.Duration;

public class AdminBaseTest extends BaseTest{

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setup")
    public void loginAsAdmin(){
        waitUntilLoginPageReady();
        LoginPage loginPage = new LoginPage(driver);
        PropertyFile propertyFile = new PropertyFile();
        String username = propertyFile.getValueProperty("adminUsername");
        String password = propertyFile.getValueProperty("adminPassword");

        loginPage.loginAsPlacementCell(username, password);
        waitUntilRouteContains("/admin/home");
        log.info("Admin logged in successfully");
    }

    public void navigateToAdminDashboardPage() {
        ensureAdminSession();
        if (!driver.getCurrentUrl().contains("/admin/home")) {
            new AdminDashboardPage(driver).clickDashboardLink();
        }
        log.info("Navigate to Admin Dashboard page successfully");
    }

    public void navigateToJobPostingPage(){
        ensureAdminSession();
        if (!driver.getCurrentUrl().contains("/admin/jobs")) {
            new AdminDashboardPage(driver).clickJobPostingLink();
        }
        log.info("Navigate to Job Posting page successfully");
    }

    public void navigateToApplicationMngmntPage(){
        ensureAdminSession();
        if (!driver.getCurrentUrl().contains("/admin/applications")) {
            new AdminDashboardPage(driver).clickApplicationManagementLink();
        }
        log.info("Navigate to Application Management page successfully");
    }

    public void navigateToReportAnalysisPage(){
        ensureAdminSession();
        if (!driver.getCurrentUrl().contains("/admin/reports")) {
            new AdminDashboardPage(driver).clickReportsLink();
        }
        log.info("Navigate to Report Analysis page successfully");
    }

    private void ensureAdminSession() {
        if (driver.getCurrentUrl().contains("/login")) {
            loginAsAdmin();
        }
    }

    private void waitUntilRouteContains(String route) {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains(route));
    }

    private void waitUntilLoginPageReady() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("login-submit-btn")));
    }
}
