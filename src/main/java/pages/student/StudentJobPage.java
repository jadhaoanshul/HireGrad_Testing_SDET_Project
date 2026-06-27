package pages.student;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class StudentJobPage extends BasePage {

    private WebDriver driver;

    @FindBy(id = "jobs-search-input")
    private WebElement searchInput;

    @FindBy(id = "jobs-filters-toggle-btn")
    private WebElement filtersToggleButton;

    @FindBy(xpath = "//h1[normalize-space()='Job applications']")
    private WebElement jobsPageHeading;

    @FindBy(xpath = "//p[contains(normalize-space(),'jobs found') or contains(normalize-space(),'job found')]")
    private WebElement jobsFoundText;

    @FindBy(css = "article[apptilt]")
    private List<WebElement> jobCards;

    @FindBy(xpath = "//p[normalize-space()='No jobs match your filters yet. Check back soon!']")
    private List<WebElement> noJobsMessage;

    public StudentJobPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean isJobsPageDisplayed() {
        return jobsPageHeading.isDisplayed()
                && searchInput.isDisplayed()
                && filtersToggleButton.isDisplayed();
    }

    public void enterSearchKeyword(String keyword) {
        searchInput.clear();
        searchInput.sendKeys(keyword);
    }

    public String getSearchInputValue() {
        return searchInput.getAttribute("value");
    }

    public void clickFiltersButton() {
        filtersToggleButton.click();
    }

    public String getJobsFoundText() {
        return jobsFoundText.getText();
    }

    public int getJobsFoundCount() {
        String count = jobsFoundText.getText().replaceAll("[^0-9]", "");
        return count.isEmpty() ? 0 : Integer.parseInt(count);
    }

    public boolean hasJobListings() {
        return !jobCards.isEmpty();
    }

    public int getJobCardsCount() {
        return jobCards.size();
    }

    public String getJobTitle(int index) {
        return jobCards.get(index)
                .findElement(By.cssSelector("h3"))
                .getText();
    }

    public String getCompanyName(int index) {
        return jobCards.get(index)
                .findElement(By.cssSelector("h3 + p"))
                .getText();
    }

    public String getJobCardText(int index) {
        return jobCards.get(index).getText();
    }

    public List<String> getAllJobTitles() {
        List<String> titles = new ArrayList<>();

        for (WebElement jobCard : jobCards) {
            titles.add(jobCard.findElement(By.cssSelector("h3")).getText());
        }

        return titles;
    }

    public List<String> getAllCompanyNames() {
        List<String> companyNames = new ArrayList<>();

        for (WebElement jobCard : jobCards) {
            companyNames.add(jobCard.findElement(By.cssSelector("h3 + p")).getText());
        }

        return companyNames;
    }

    public boolean isJobDisplayed(String jobTitle) {
        for (WebElement jobCard : jobCards) {
            if (jobCard.getText().contains(jobTitle)) {
                return true;
            }
        }

        return false;
    }

    public void clickApplyButton(int index) {
        jobCards.get(index)
                .findElement(By.xpath(".//button[normalize-space()='Apply']"))
                .click();
    }

    public boolean isApplyButtonDisplayed(int index) {
        return jobCards.get(index)
                .findElement(By.xpath(".//button[normalize-space()='Apply']"))
                .isDisplayed();
    }

    public boolean isApplyButtonEnabled(int index) {
        return jobCards.get(index)
                .findElement(By.xpath(".//button[normalize-space()='Apply']"))
                .isEnabled();
    }

    public boolean isNoJobsMessageDisplayed() {
        return !noJobsMessage.isEmpty() && noJobsMessage.get(0).isDisplayed();
    }

    public String getNoJobsMessage() {
        return noJobsMessage.get(0).getText();
    }

    public boolean isProfileIncompleteMessageDisplayed(int index) {
        return jobCards.get(index)
                .getText()
                .contains("Complete your personal info, academic details, and resume to apply.");
    }

    public void clickFilterOption(String optionText) {
        driver.findElement(By.xpath(
                "//*[self::button or self::label or self::span][normalize-space()='" + optionText + "']"
        )).click();
    }
}