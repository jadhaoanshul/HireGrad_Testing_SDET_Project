package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminLayoutPage extends BasePage {
    @FindBy(id = "admin-layout-account-btn")
    private WebElement accountButton;

    @FindBy(id = "admin-layout-profile-btn")
    private WebElement profileButton;

    @FindBy(id = "admin-layout-logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "admin-layout-bell-btn")
    private WebElement bellButton;

    private final By notificationPanel = By.id("admin-layout-notifications-panel");

    public AdminLayoutPage(WebDriver driver) {
        super(driver);
    }

    public void goHome() {
        navigate("/admin/home");
    }

    public void goJobs() {
        navigate("/admin/jobs");
    }

    public void goApplications() {
        navigate("/admin/applications");
    }

    public void goStudents() {
        navigate("/admin/students");
    }

    public void goReports() {
        navigate("/admin/reports");
    }

    public void goProfile() {
        action.click(accountButton);
        action.click(profileButton);
        action.waitForUrlContains("/admin/profile");
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
        action.click(By.id("admin-layout-nav-" + route));
        action.waitForUrlContains(route);
    }
}
