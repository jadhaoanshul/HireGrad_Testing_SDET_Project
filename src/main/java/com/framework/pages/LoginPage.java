package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id = "login-role-student-tab")
    private WebElement studentRole;

    @FindBy(id = "login-role-admin-tab")
    private WebElement adminRole;

    @FindBy(id = "login-username-input")
    private WebElement usernameInput;

    @FindBy(id = "login-password-input")
    private WebElement passwordInput;

    @FindBy(id = "login-submit-btn")
    private WebElement signInButton;

    @FindBy(id = "login-password-toggle-btn")
    private WebElement passwordToggleButton;

    private final By loginPage = By.id("login-page");
    private final By errorMessage = By.cssSelector("[role='alert']");
    private final By validationMessage = By.cssSelector("p.text-red-500");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return displayed(loginPage);
    }

    public void selectAdminRole() {
        action.click(adminRole);
    }

    public void selectStudentRole() {
        action.click(studentRole);
    }

    public void loginAsAdmin(String username, String password) {
        selectAdminRole();
        submit(username, password);
    }

    public void loginAsStudent(String username, String password) {
        selectStudentRole();
        submit(username, password);
    }

    public void submit(String username, String password) {
        action.enterText(usernameInput, username);
        action.enterText(passwordInput, password);
        action.click(signInButton);
    }

    public void submitBlank() {
        action.click(signInButton);
    }

    public boolean isErrorDisplayed() {
        return displayed(errorMessage);
    }

    public boolean isValidationDisplayed() {
        return displayed(validationMessage) || isErrorDisplayed();
    }

    public void togglePassword() {
        action.click(passwordToggleButton);
    }

    public String passwordType() {
        return passwordInput.getAttribute("type");
    }
}
