package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StudentLayoutPage extends BasePage {
    @FindBy(id = "student-layout-account-btn")
    private WebElement accountButton;

    @FindBy(id = "student-layout-profile-btn")
    private WebElement profileButton;

    @FindBy(id = "student-layout-logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "student-layout-bell-btn")
    private WebElement bellButton;

    private final By notificationPanel = By.id("student-layout-notifications-panel");

    public StudentLayoutPage(WebDriver driver) {
        super(driver);
    }

    public void goHome() {
        navigate("/student/home");
    }

    public void goJobs() {
        navigate("/student/jobs");
    }

    public void goTracker() {
        navigate("/student/tracker");
    }

    public void goProfile() {
        action.click(accountButton);
        action.click(profileButton);
        action.waitForUrlContains("/student/profile");
    }

    public void logout() {
        action.click(accountButton);
        action.click(logoutButton);
        action.waitForUrlContains("/login");
    }

    public void openNotifications() {
        action.click(bellButton);
    }

    public boolean isNotificationPanelDisplayed() {
        return displayed(notificationPanel);
    }

    private void navigate(String route) {
        action.click(By.id("student-layout-nav-" + route));
        action.waitForUrlContains(route);
    }
}
