package pages.admin;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminDashboardPage extends BasePage {

    //private final WebDriver driver;
    private final WebDriverWait wait;

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='relative z-10 flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between']")
    private WebElement adminDashboardHeading;

    @FindBy(xpath = "//a[@id='admin-home-summary-Active postings']")
    private WebElement activePostingsCount;

    @FindBy(xpath = "//a[@id='admin-home-summary-Total applicants']")
    private WebElement totalApplicantCount;

    @FindBy(xpath = "//a[@id='admin-home-summary-Selected']")
    private WebElement selectedCount;

    @FindBy(xpath = "//a[@id='admin-home-summary-Pending reviews']")
    private WebElement pendingReviewsCount;

    @FindBy(xpath = "//div[@id='admin-home-postings-card']")
    private WebElement postingOverview;

    @FindBy(xpath = "//div[@id='admin-home-placement-rate-card']")
    private WebElement selectionRate;

    @FindBy(xpath = "//div[@id='admin-home-recent-activity-card']")
    private WebElement recentActivity;

    @FindBy(id = "admin-layout-nav-/admin/home")
    private WebElement dashboardSidebarLink;

    @FindBy(id = "admin-layout-nav-/admin/jobs")
    private WebElement jobPostingSidebarLink;

    @FindBy(id = "admin-layout-nav-/admin/applications")
    private WebElement applicationManagementSidebarLink;

    //a[@id='admin-layout-nav-/admin/students']//*[name()='svg'] -- student signup

    @FindBy(id = "admin-layout-nav-/admin/reports")
    private WebElement reportsSidebarLink;

    @FindBy(xpath = "//button[@id='admin-layout-profile-btn']")
    private WebElement profileSidebarLink;

    @FindBy(xpath = "//button[@id='admin-layout-logout-btn']")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'loading')]")
    private List<WebElement> loadingMessages;

    @FindBy(xpath = "//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'error') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'failed') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'unable')]")
    private List<WebElement> errorMessages;

    public boolean isAdminDashboardPageLoaded() {
        wait.until(ExpectedConditions.urlContains("/admin/home"));
        return driver.getCurrentUrl().contains("/admin/home")
                && isElementDisplayed(adminDashboardHeading);
    }

    public boolean isAdminDashboardHeadingDisplayed() {
        return isElementDisplayed(adminDashboardHeading);
    }

    public boolean arePlacementSummaryCardsDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                activePostingsCount,
                totalApplicantCount,
                selectedCount,
                pendingReviewsCount));
    }

    public boolean arePostingOverviewDetailsDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                postingOverview,
                selectionRate,
                recentActivity));
    }

    public boolean areAllAdminDashboardComponentsDisplayed() {
        return isAdminDashboardHeadingDisplayed()
                && arePlacementSummaryCardsDisplayed()
                && arePostingOverviewDetailsDisplayed();
    }

    public boolean isDashboardContentOrStatusMessageDisplayed() {
        return areAllAdminDashboardComponentsDisplayed()
                || isAnyElementDisplayed(loadingMessages)
                || isAnyElementDisplayed(errorMessages);
    }

    public void clickDashboardLink() {
        clickElement(dashboardSidebarLink);
        wait.until(ExpectedConditions.urlContains("/admin/home"));
    }

    public void clickJobPostingLink() {
        clickElement(jobPostingSidebarLink);
        wait.until(ExpectedConditions.urlContains("/admin/jobs"));
    }

    public void clickApplicationManagementLink() {
        clickElement(applicationManagementSidebarLink);
        wait.until(ExpectedConditions.urlContains("application"));
    }

    public void clickReportsLink() {
        clickElement(reportsSidebarLink);
        wait.until(ExpectedConditions.urlContains("report"));
    }

    public void clickProfileLink() {
        clickElement(profileSidebarLink);
        wait.until(ExpectedConditions.urlContains("profile"));
    }

    public void clickLogoutButton() {
        clickElement(logoutButton);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/login"),
                ExpectedConditions.urlContains("login")));
    }

    public boolean isJobPostingPageRouteDisplayed() {
        return waitUntilUrlContains("/admin/jobs");
    }

    public boolean isApplicationManagementPageRouteDisplayed() {
        return waitUntilUrlContains("/admin/applications");
    }

    public boolean isReportsPageRouteDisplayed() {
        return waitUntilUrlContains("/admin/reports");
    }

    public boolean isProfilePageRouteDisplayed() {
        return waitUntilUrlContains("/admin/profile") || driver.getCurrentUrl().toLowerCase().contains("profile");
    }

    public boolean isLoginPageRouteDisplayed() {
        return waitUntilUrlContains("/login") || driver.getCurrentUrl().toLowerCase().contains("login");
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    private void clickElement(WebElement locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private boolean isElementDisplayed(WebElement locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(locator));
            return element.isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean areAllElementsDisplayed(List<WebElement> elements) {
        for (WebElement element : elements) {
            if (!isElementDisplayed(element)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAnyElementDisplayed(List<WebElement> elements) {
        for (WebElement element : elements) {
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    private boolean waitUntilUrlContains(String route) {
        try {
            wait.until(ExpectedConditions.urlContains(route));
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
