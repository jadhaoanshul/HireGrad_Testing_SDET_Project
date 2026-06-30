package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ApplicationManagementPage extends BasePage {
    @FindBy(id = "appmgmt-search-input")
    private WebElement searchInput;
    @FindBy(id = "appmgmt-branch-filter")
    private WebElement branchFilter;
    @FindBy(id = "appmgmt-reset-btn")
    private WebElement resetButton;

    private final By page = By.id("appmgmt-page");
    private final By jobCards = By.cssSelector("[id^='appmgmt-job-card-']");
    private final By rows = By.cssSelector("#appmgmt-applicants-table tbody tr");
    private final By noData = By.xpath("//p[contains(.,'No applications') or contains(.,'No applicants')]");
    private final By statusDropdowns = By.cssSelector("#appmgmt-applicants-table tbody select");

    public ApplicationManagementPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return displayed(page) && (count(jobCards) > 0 || displayed(noData) || present(By.id("appmgmt-stats-bar")));
    }

    public void resetFilters() {
        if (action.isDisplayed(resetButton)) {
            action.click(resetButton);
        }
    }

    public void search(String value) {
        action.enterText(searchInput, value);
    }

    public void filterByFirstBranchOption() {
        action.selectFirstRealOption(By.id("appmgmt-branch-filter"));
    }

    public String firstBranchOptionText() {
        Select select = new Select(branchFilter);
        for (WebElement option : select.getOptions()) {
            String value = option.getAttribute("value");
            if (value != null && !value.trim().isEmpty()) {
                return option.getText().trim();
            }
        }
        return "";
    }

    public int rowCount() {
        return count(rows);
    }

    public boolean isNoDataDisplayed() {
        return displayed(noData);
    }

    public String firstRowSearchText() {
        List<WebElement> tableRows = all(rows);
        return tableRows.isEmpty() ? "" : tableRows.get(0).getText().split("\\R")[0].trim();
    }

    public boolean everyRowContains(String value) {
        return everyRowContains(rows, value);
    }

    public boolean everyRowContainsBranch(String branch) {
        return everyRowContains(rows, branch);
    }

    public void changeFirstStatus() {
        List<WebElement> dropdowns = all(statusDropdowns);
        if (!dropdowns.isEmpty()) {
            Select select = new Select(dropdowns.get(0));
            String current = select.getFirstSelectedOption().getText().trim();
            select.selectByVisibleText(current.equalsIgnoreCase("Selected") ? "Rejected" : "Selected");
        }
    }

    public boolean hasStatusDropdown() {
        return count(statusDropdowns) > 0;
    }
}
