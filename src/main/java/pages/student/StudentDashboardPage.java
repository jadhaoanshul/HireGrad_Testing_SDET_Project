package pages.student;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudentDashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public StudentDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath ="//section[@id='student-home-hero']")
    private WebElement studentDashBoardHeading;
    @FindBy(xpath="//a[@id='student-home-summary-Applications submitted']")
    private WebElement applicationSubmittedCount;
    @FindBy(xpath = "//a[@id='student-home-summary-In review']")
    private WebElement inReviewCount;
    @FindBy(xpath="//a[@id='student-home-summary-Selected']")
    private WebElement selectedCount;
    @FindBy(xpath="//a[@id='student-home-summary-Eligible roles']")
    private WebElement eligibleRoleCount;
    @FindBy(xpath = "//div[@id='student-home-profile-completeness-card']")
    private WebElement profileCompleteness;
    @FindBy(xpath="//div[@id='student-home-recent-applications-card']")
    private WebElement recentApplications;
    @FindBy(xpath="//a[@id='student-home-quick-action-Browse jobs']")
    private WebElement browseJobs;
    @FindBy(xpath="//a[@id='student-home-quick-action-View tracker']")
    private WebElement viewTracker;
    @FindBy(xpath="//a[@id='student-home-quick-action-Complete profile']")
    private WebElement completeProfile;
    @FindBy(xpath="//a[@id='student-layout-nav-/student/jobs']")
    private WebElement jobDashBoardLink;
    @FindBy(xpath="//a[@id='student-layout-nav-/student/tracker']")
    private WebElement jobTrackerLink;
    @FindBy(xpath="//button[@id='bot-launcher-btn']")
    private WebElement botLaunchButton;
    @FindBy(xpath="//button[@id='student-layout-account-btn']")
    private WebElement navProfileButton;
    @FindBy(xpath="//button[@id='student-layout-profile-btn']")
    private WebElement profileButton;
    @FindBy(xpath="//button[@id='student-layout-logout-btn']")
    private WebElement logoutButton;



    public boolean isStudentDashboardPageLoaded() {
        wait.until(ExpectedConditions.urlContains("/student/home"));
        return driver.getCurrentUrl().contains("/student/home")
                && isElementDisplayed(studentDashBoardHeading);
    }

    public boolean isStudentDashboardHeadingDisplayed() {
        return isElementDisplayed(studentDashBoardHeading);
    }

    public boolean areApplicationsSummaryCardsDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                applicationSubmittedCount,
                inReviewCount,
                selectedCount,
                eligibleRoleCount,
                browseJobs,
                viewTracker,
                completeProfile));
    }

    public boolean areRecentDetailsDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                recentApplications,
                profileCompleteness));
    }

    public boolean areAllStudentDashboardComponentsDisplayed() {
        return isStudentDashboardHeadingDisplayed()
                && areRecentDetailsDisplayed();
    }

    public void clickJobDashBoardLink() {
        clickElement(jobDashBoardLink);
        wait.until(ExpectedConditions.urlContains("/student/jobs"));
    }

    public void clickJobTrackerLink() {
        clickElement(jobTrackerLink);
        wait.until(ExpectedConditions.urlContains("/student/tracker"));
    }

    public void clickProfileButton() {
        clickElement(navProfileButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='student-layout-profile-btn']")));
    }

    public void clickProfileLink() {
        clickElement(navProfileButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='student-layout-profile-btn']")));
        clickElement(profileButton);
        wait.until(ExpectedConditions.urlContains("/student/profile"));
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
