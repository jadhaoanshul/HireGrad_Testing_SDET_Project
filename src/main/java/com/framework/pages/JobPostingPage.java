package com.framework.pages;

import com.framework.base.BasePage;
import com.framework.util.GenericUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobPostingPage extends BasePage {
    @FindBy(id = "post-company-input")
    private WebElement companyInput;
    @FindBy(id = "post-job-title-input")
    private WebElement titleInput;
    @FindBy(id = "post-location-input")
    private WebElement locationInput;
    @FindBy(id = "post-ctc-input")
    private WebElement ctcInput;
    @FindBy(id = "post-min-cgpa-input")
    private WebElement minCgpaInput;
    @FindBy(id = "post-skill-input")
    private WebElement skillInput;
    @FindBy(id = "post-add-skill-btn")
    private WebElement addSkillButton;
    @FindBy(id = "post-description-textarea")
    private WebElement descriptionTextArea;
    @FindBy(id = "post-deadline-input")
    private WebElement deadlineInput;
    @FindBy(id = "post-publish-btn")
    private WebElement publishButton;
    @FindBy(id = "post-cancel-btn")
    private WebElement cancelButton;

    private final By page = By.id("post-form");
    private final By validationMessage = By.cssSelector("#post-form p.text-red-500, #post-toast");

    public JobPostingPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return displayed(page)
                && displayed(By.id("post-company-role-section"))
                && displayed(By.id("post-eligibility-section"))
                && displayed(By.id("post-description-section"));
    }

    public void fillValidJob(String companyPrefix) {
        action.enterText(companyInput, GenericUtil.uniqueText(companyPrefix));
        action.enterText(titleInput, "Software Engineer");
        action.enterText(locationInput, "Chennai");
        action.enterText(ctcInput, "600000");
        action.click(By.id("post-employment-type-FULL_TIME"));
        action.click(By.id("post-work-mode-ON_SITE"));
        action.enterText(minCgpaInput, "7.0");
        addSkill("Java");
        action.enterText(descriptionTextArea, "Campus placement role for automation validation.");
        enterDeadline(GenericUtil.futureDateTimeLocal(10));
    }

    public void fillInvalidJobData() {
        action.enterText(companyInput, "1234");
        action.enterText(titleInput, "124");
        action.enterText(locationInput, "1234");
        action.enterText(ctcInput, "500000");
        action.click(By.id("post-employment-type-FULL_TIME"));
        action.click(By.id("post-work-mode-ON_SITE"));
        action.enterText(minCgpaInput, "7.0");
        addSkill("1234");
        action.enterText(descriptionTextArea, "Invalid job detail validation test.");
        enterDeadline(GenericUtil.futureDateTimeLocal(10));
    }

    public void fillInvalidCtcCgpaAndPastDeadline() {
        action.enterText(companyInput, "Valid Company");
        action.enterText(titleInput, "Graduate Engineer");
        action.enterText(locationInput, "Bengaluru");
        action.enterText(ctcInput, "-1");
        action.enterText(minCgpaInput, "11");
        addSkill("Java");
        action.enterText(descriptionTextArea, "Invalid boundary values.");
        enterDeadline(GenericUtil.pastDeadlineSpaced(1));
    }

    public void addSkill(String skill) {
        action.enterText(skillInput, skill);
        action.click(addSkillButton);
    }

    /**
     * Enters a deadline into the datetime-local input after normalizing it to the
     * "yyyy-MM-dd'T'HH:mm" format so the date and time segments are properly
     * separated regardless of the incoming format.
     */
    public void enterDeadline(String rawDeadline) {
        action.enterText(deadlineInput, GenericUtil.formatForDateTimeLocal(rawDeadline));
    }

    public void publish() {
        action.click(publishButton);
    }

    public void cancel() {
        action.click(cancelButton);
    }

    public boolean isValidationDisplayed() {
        return displayed(validationMessage);
    }
}
