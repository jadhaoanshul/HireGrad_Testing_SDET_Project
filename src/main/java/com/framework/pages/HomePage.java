package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isAdminDashboardLoaded() {
        return displayed(By.id("admin-home-hero"))
                && displayed(By.id("admin-home-summary-section"));
    }

    public boolean areAdminDashboardCardsDisplayed() {
        return count(By.cssSelector("[id^='admin-home-summary-']")) >= 4
                && displayed(By.id("admin-home-postings-card"))
                && displayed(By.id("admin-home-placement-rate-card"))
                && displayed(By.id("admin-home-recent-activity-card"));
    }

    public boolean isStudentDashboardLoaded() {
        return displayed(By.id("student-home-hero"))
                && displayed(By.id("student-home-summary-section"));
    }

    public boolean areStudentDashboardCardsDisplayed() {
        return count(By.cssSelector("[id^='student-home-summary-']")) >= 4
                && displayed(By.id("student-home-profile-completeness-card"))
                && displayed(By.id("student-home-recent-applications-card"));
    }
}
