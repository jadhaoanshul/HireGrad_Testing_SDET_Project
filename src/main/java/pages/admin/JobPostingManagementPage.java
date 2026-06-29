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

public class JobPostingManagementPage extends BasePage {

    private final WebDriverWait wait;

    public JobPostingManagementPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath = "//h1[normalize-space()='Post new job']")
    private WebElement jobPostingHeading;

    @FindBy(xpath = "//input[@id='post-company-input']")
    private WebElement companyNameTextbox;

    @FindBy(xpath = "//input[@id='post-company-input']/following::p[1]")
    private WebElement companyNameRequiredMessage;

    @FindBy(xpath = "//input[@id='post-job-title-input']")
    private WebElement jobTitleTextbox;

    @FindBy(xpath = "//input[@id='post-job-title-input']/following::p[1]")
    private WebElement jobTitleRequiredMessage;

    @FindBy(xpath = "//input[@id='post-location-input']")
    private WebElement locationTextbox;

    @FindBy(xpath = "//input[@id='post-location-input']/following::p[1]")
    private WebElement locationRequiredMessage;

    @FindBy(xpath = "//input[@id='post-ctc-input']")
    private WebElement ctcField;

    @FindBy(xpath = "//input[@id='post-ctc-input']/following::p[1]")
    private WebElement ctcRequiredMessage;

    @FindBy(xpath = "//button[normalize-space()='Full-time' or normalize-space()='Internship' or normalize-space()='Part-time']")
    private List<WebElement> employmentTypeButtons;

    @FindBy(xpath = "//section[@id='post-job-basic-section']//button[contains(@id,'post-employment-type')]/following::p[1]")
    private WebElement employmentTypeRequiredMessage;

    @FindBy(xpath = "//button[normalize-space()='On-site' or normalize-space()='Hybrid' or normalize-space()='Remote']")
    private List<WebElement> workModeButtons;

    @FindBy(xpath = "//button[contains(@id,'post-work-mode')]/following::p[1]")
    private WebElement workModeRequiredMessage;

    @FindBy(xpath = "//input[@id='post-min-cgpa-input']")
    private WebElement minimumCgpaField;

    @FindBy(xpath = "//input[@id='post-min-cgpa-input']/following::p[1]")
    private WebElement minimumCgpaRequiredMessage;

    @FindBy(xpath = "//input[@id='post-skill-input']")
    private WebElement requiredSkillsInput;

    @FindBy(xpath = "//input[@id='post-skill-input']/following::p[1]")
    private WebElement requiredSkillsRequiredMessage;

    @FindBy(xpath = "//button[@id='post-add-skill-btn']")
    private WebElement skillsAddButton;

    @FindBy(xpath = "//textarea[@id='post-description-textarea']")
    private WebElement jobDescriptionTextbox;

    @FindBy(xpath = "//textarea[@id='post-description-textarea']/following::p[1]")
    private WebElement jobDescriptionRequiredMessage;

    @FindBy(xpath = "//input[@id='post-deadline-input']")
    private WebElement applicationDeadlineField;

    @FindBy(xpath = "//input[@id='post-deadline-input']/following::p[1]")
    private WebElement applicationDeadlineRequiredMessage;

    @FindBy(xpath = "//button[@id='post-publish-btn']")
    private WebElement publishButton;

    @FindBy(xpath = "//button[@id='post-cancel-btn']")
    private WebElement cancelButton;

    public boolean isJobPostingPageLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("/admin/jobs"));
            return driver.getCurrentUrl().contains("/admin/jobs")
                    && isElementDisplayed(jobPostingHeading);
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean isJobPostingHeadingDisplayed() {
        return isElementDisplayed(jobPostingHeading);
    }

    public boolean areAllJobPostingFieldsDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                companyNameTextbox,
                jobTitleTextbox,
                locationTextbox,
                ctcField,
                minimumCgpaField,
                requiredSkillsInput,
                skillsAddButton,
                jobDescriptionTextbox,
                applicationDeadlineField,
                publishButton,
                cancelButton))
                && areEmploymentTypeButtonsDisplayed()
                && areWorkModeButtonsDisplayed();
    }

    public boolean areEmploymentTypeButtonsDisplayed() {
        return areAllElementsInListDisplayed(employmentTypeButtons);
    }

    public boolean areWorkModeButtonsDisplayed() {
        return areAllElementsInListDisplayed(workModeButtons);
    }

    public void enterCompanyName(String companyName) {
        clearAndType(companyNameTextbox, companyName);
    }

    public void enterJobTitle(String jobTitle) {
        clearAndType(jobTitleTextbox, jobTitle);
    }

    public void enterJobLocation(String location) {
        clearAndType(locationTextbox, location);
    }

    public void enterCTC(String ctc) {
        clearAndType(ctcField, ctc);
    }

    public void selectEmploymentType(String employmentType) {
        clickButtonByText(employmentTypeButtons, employmentType);
    }

    public void selectWorkMode(String workMode) {
        clickButtonByText(workModeButtons, workMode);
    }

    public void selectFirstEmploymentType() {
        clickFirstButton(employmentTypeButtons);
    }

    public void selectFirstWorkMode() {
        clickFirstButton(workModeButtons);
    }

    public void enterMinimumCGPA(String cgpa) {
        clearAndType(minimumCgpaField, cgpa);
    }

    public void addRequiredSkill(String skill) {
        clearAndType(requiredSkillsInput, skill);
        clickElement(skillsAddButton);
    }

    public void enterJobDescription(String description) {
        clearAndType(jobDescriptionTextbox, description);
    }

    public void selectApplicationDeadline(String deadline) {
        clearAndType(applicationDeadlineField, deadline);
    }

    public void clickPublishButton() {
        clickElement(publishButton);
    }

    public void clickCancelButton() {
        clickElement(cancelButton);
    }

    public void createJobPosting(
            String companyName,
            String jobTitle,
            String location,
            String ctc,
            String employmentType,
            String workMode,
            String minimumCgpa,
            String requiredSkill,
            String jobDescription,
            String deadline) {

        enterCompanyName(companyName);
        enterJobTitle(jobTitle);
        enterJobLocation(location);
        enterCTC(ctc);
        selectEmploymentType(employmentType);
        selectWorkMode(workMode);
        enterMinimumCGPA(minimumCgpa);
        addRequiredSkill(requiredSkill);
        enterJobDescription(jobDescription);
        selectApplicationDeadline(deadline);
        clickPublishButton();
    }

    public void enterInvalidCompanyName(String invalidCompanyName) {
        enterCompanyName(invalidCompanyName);
    }

    public void enterInvalidJobTitle(String invalidJobTitle) {
        enterJobTitle(invalidJobTitle);
    }

    public void enterInvalidJobLocation(String invalidLocation) {
        enterJobLocation(invalidLocation);
    }

    public void enterInvalidRequiredSkill(String invalidSkill) {
        addRequiredSkill(invalidSkill);
    }

    public boolean isCompanyNameRequiredMessageDisplayed() {
        return isElementDisplayed(companyNameRequiredMessage);
    }

    public boolean isJobTitleRequiredMessageDisplayed() {
        return isElementDisplayed(jobTitleRequiredMessage);
    }

    public boolean isLocationRequiredMessageDisplayed() {
        return isElementDisplayed(locationRequiredMessage);
    }

    public boolean isCtcRequiredMessageDisplayed() {
        return isElementDisplayed(ctcRequiredMessage);
    }

    public boolean isEmploymentTypeRequiredMessageDisplayed() {
        return isElementDisplayed(employmentTypeRequiredMessage);
    }

    public boolean isWorkModeRequiredMessageDisplayed() {
        return isElementDisplayed(workModeRequiredMessage);
    }

    public boolean isMinimumCgpaRequiredMessageDisplayed() {
        return isElementDisplayed(minimumCgpaRequiredMessage);
    }

    public boolean isRequiredSkillsRequiredMessageDisplayed() {
        return isElementDisplayed(requiredSkillsRequiredMessage);
    }

    public boolean isJobDescriptionRequiredMessageDisplayed() {
        return isElementDisplayed(jobDescriptionRequiredMessage);
    }

    public boolean isApplicationDeadlineRequiredMessageDisplayed() {
        return isElementDisplayed(applicationDeadlineRequiredMessage);
    }

    public boolean areAllRequiredMessagesDisplayed() {
        return areAllElementsDisplayed(Arrays.asList(
                companyNameRequiredMessage,
                jobTitleRequiredMessage,
                locationRequiredMessage,
                ctcRequiredMessage,
                employmentTypeRequiredMessage,
                workModeRequiredMessage,
                minimumCgpaRequiredMessage,
                requiredSkillsRequiredMessage,
                jobDescriptionRequiredMessage,
                applicationDeadlineRequiredMessage));
    }

    public String getCompanyNameRequiredMessageText() {
        return getText(companyNameRequiredMessage);
    }

    public String getJobTitleRequiredMessageText() {
        return getText(jobTitleRequiredMessage);
    }

    public String getLocationRequiredMessageText() {
        return getText(locationRequiredMessage);
    }

    public String getCtcRequiredMessageText() {
        return getText(ctcRequiredMessage);
    }

    public String getEmploymentTypeRequiredMessageText() {
        return getText(employmentTypeRequiredMessage);
    }

    public String getWorkModeRequiredMessageText() {
        return getText(workModeRequiredMessage);
    }

    public String getMinimumCgpaRequiredMessageText() {
        return getText(minimumCgpaRequiredMessage);
    }

    public String getRequiredSkillsRequiredMessageText() {
        return getText(requiredSkillsRequiredMessage);
    }

    public String getJobDescriptionRequiredMessageText() {
        return getText(jobDescriptionRequiredMessage);
    }

    public String getApplicationDeadlineRequiredMessageText() {
        return getText(applicationDeadlineRequiredMessage);
    }

    public boolean isRedirectedToApplicationManagementPage() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.urlContains("/admin/applications"));
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean isStillOnJobPostingPage() {
        return driver.getCurrentUrl().contains("/admin/jobs");
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    private void clearAndType(WebElement locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(locator));
        element.clear();
        element.sendKeys(value);
    }

    private void clickButtonByText(List<WebElement> buttons, String buttonText) {
        for (WebElement button : buttons) {
            if (button.getText().trim().equalsIgnoreCase(buttonText)) {
                wait.until(ExpectedConditions.elementToBeClickable(button)).click();
                return;
            }
        }
        throw new IllegalArgumentException("Button not found with text: " + buttonText);
    }

    private void clickFirstButton(List<WebElement> buttons) {
        if (buttons == null || buttons.isEmpty()) {
            throw new IllegalStateException("No button options are available");
        }
        wait.until(ExpectedConditions.elementToBeClickable(buttons.get(0))).click();
    }

    private void clickElement(WebElement locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private String getText(WebElement locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(locator));
            return element.getText();
        } catch (Exception exception) {
            return "";
        }
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

    private boolean areAllElementsInListDisplayed(List<WebElement> elements) {
        if (elements == null || elements.isEmpty()) {
            return false;
        }
        for (WebElement element : elements) {
            if (!isElementDisplayed(element)) {
                return false;
            }
        }
        return true;
    }
}
