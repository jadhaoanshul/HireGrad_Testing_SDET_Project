package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StudentProfilePage extends BasePage {
    @FindBy(id = "profile-edit-toggle-btn")
    private WebElement editButton;
    @FindBy(id = "profile-save-btn")
    private WebElement saveButton;
    @FindBy(id = "profile-cancel-btn")
    private WebElement cancelButton;
    @FindBy(id = "profile-first-name-input")
    private WebElement firstNameInput;
    @FindBy(id = "profile-last-name-input")
    private WebElement lastNameInput;
    @FindBy(id = "profile-personal-email-input")
    private WebElement personalEmailInput;
    @FindBy(id = "profile-phone-input")
    private WebElement phoneInput;

    public StudentProfilePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForReady();
        try {
            action.waitForVisible(By.id("profile-photo-section"));
            action.waitForVisible(By.id("profile-personal-info-section"));
            action.waitForVisible(By.id("profile-links-section"));
            action.waitForVisible(By.id("profile-edit-toggle-btn"));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private void waitForReady() {
        // The Student profile page loads its data asynchronously and keeps the
        // form disabled and inputs unrendered until loading completes. Waiting for
        // the form inputs to be present guarantees the data has finished loading
        // before we assert or interact (avoids a race where an early Edit click is
        // undone when the load handler re-disables the form).
        action.waitForPresent(By.id("profile-first-name-input"));
    }

    public void updateValidProfile(String firstName, String lastName, String email, String phone) {
        waitForReady();
        action.click(editButton);
        action.enterText(firstNameInput, firstName);
        action.enterText(lastNameInput, lastName);
        action.enterText(personalEmailInput, email);
        action.enterText(phoneInput, phone);
        action.click(saveButton);
    }

    public void enterInvalidProfileData() {
        waitForReady();
        action.click(editButton);
        action.enterText(personalEmailInput, "invalid-email");
        action.enterText(phoneInput, "abc");
        action.click(saveButton);
    }

    public void cancelAfterEdit(String firstName) {
        waitForReady();
        action.click(editButton);
        action.enterText(firstNameInput, firstName);
        action.click(cancelButton);
    }

    public boolean isValidationDisplayed() {
        return displayed(By.cssSelector("#profile-personal-info-section .text-red-500, #profile-personal-info-section [class*='red']"));
    }
}
