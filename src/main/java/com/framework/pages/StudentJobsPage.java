package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StudentJobsPage extends BasePage {
    @FindBy(id = "jobs-search-input")
    private WebElement searchInput;
    @FindBy(id = "jobs-filters-toggle-btn")
    private WebElement filtersToggleButton;
    @FindBy(id = "jobs-clear-filters-btn")
    private WebElement clearFiltersButton;

    private final By jobCards = By.cssSelector("[id^='jobs-job-card-'], [id^='jobs-card-'], article");
    private final By applyButtons = By.cssSelector("[id^='jobs-apply-btn-']");
    private final By filterPanel = By.id("jobs-filter-panel");

    public StudentJobsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return displayed(By.id("jobs-search-bar")) && displayed(By.id("jobs-filters-toggle-btn"));
    }

    public int jobCount() {
        return count(jobCards);
    }

    public void searchWithFirstJobText() {
        String text = firstJobText();
        if (!text.isBlank()) {
            action.enterText(searchInput, text.split("\\s+")[0]);
        }
    }

    public String firstJobText() {
        List<WebElement> jobs = all(jobCards);
        return jobs.isEmpty() ? "" : jobs.get(0).getText().trim();
    }

    public boolean everyJobContains(String value) {
        return everyRowContains(jobCards, value);
    }

    public void openFilters() {
        action.click(filtersToggleButton);
    }

    public boolean isFilterPanelDisplayed() {
        return displayed(filterPanel);
    }

    public boolean hasApplyButton() {
        return count(applyButtons) > 0;
    }

    public void clickFirstApplyButton() {
        if (hasApplyButton()) {
            action.click(all(applyButtons).get(0));
        }
    }
}
