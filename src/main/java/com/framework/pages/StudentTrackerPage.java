package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StudentTrackerPage extends BasePage {
    public StudentTrackerPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return displayed(By.id("tracker-page")) && displayed(By.id("tracker-summary-cards"));
    }

    public boolean hasTableOrEmptyState() {
        return present(By.id("tracker-table")) || displayed(By.id("tracker-browse-jobs-link"));
    }
}
