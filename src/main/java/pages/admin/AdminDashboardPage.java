package pages.admin;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminDashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
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

    @FindBy(xpath = "//a[@id='admin-layout-nav-/admin/jobs']//*[name()='svg']")
    private WebElement jobPostingSidebarLink;

    @FindBy(xpath = "//a[@id='admin-layout-nav-/admin/applications']//*[name()='svg']//*[name()='rect' and contains(@width,'8')]")
    private WebElement applicationManagementSidebarLink;

    //a[@id='admin-layout-nav-/admin/students']//*[name()='svg'] -- student signup

    @FindBy(xpath = "//a[@id='admin-layout-nav-/admin/reports']//*[name()='svg']")
    private WebElement reportsSidebarLink;

    @FindBy(xpath = "//button[@id='admin-layout-profile-btn']")
    private WebElement profileSidebarLink;

    @FindBy(xpath = "//button[@id='admin-layout-logout-btn']")
    private WebElement logoutButton;

    // @FindBy(xpath = "")
    // private WebElement loadingMessage;

    // @FindBy(xpath = "")
    // private WebElement errorMessage;

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
                && arePostingOverviewDetailsDisplayed();
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

    // public boolean isLoadingMessageDisplayed() {
    //     return isElementDisplayed(loadingMessage);
    // }

    // public boolean isErrorMessageDisplayed() {
    //     return isElementDisplayed(errorMessage);
    // }

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
}