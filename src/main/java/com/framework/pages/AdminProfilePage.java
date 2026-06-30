package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminProfilePage extends BasePage {
    @FindBy(id = "adm-profile-edit-btn")
    private WebElement editButton;
    @FindBy(id = "adm-profile-save-btn")
    private WebElement saveButton;
    @FindBy(id = "adm-profile-cancel-btn")
    private WebElement cancelButton;
    @FindBy(id = "adm-profile-first-name-input")
    private WebElement firstNameInput;
    @FindBy(id = "adm-profile-last-name-input")
    private WebElement lastNameInput;
    @FindBy(id = "adm-profile-email-input")
    private WebElement emailInput;
    @FindBy(id = "adm-profile-phone-input")
    private WebElement phoneInput;

    public AdminProfilePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForReady();
        try {
            action.waitForVisible(By.id("adm-profile-page"));
            action.waitForVisible(By.id("adm-profile-summary-card"));
            action.waitForVisible(By.id("adm-profile-identity-section"));
            action.waitForVisible(By.id("adm-profile-contact-section"));
            action.waitForVisible(By.id("adm-profile-institution-section"));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private void waitForReady() {
        // The Admin profile page loads its data asynchronously: it disables the
        // form and only renders the form inputs once loading completes. The Edit
        // button appears earlier, so clicking it too soon gets undone when the
        // load handler re-disables the form. Waiting for the form inputs to be
        // present guarantees the data has finished loading before we interact.
        action.waitForPresent(By.id("adm-profile-first-name-input"));
    }

    public void clickEdit() {
        waitForReady();
        action.click(editButton);
    }

    public void updateRequiredFields(String firstName, String lastName, String email, String phone) {
        clickEdit();
        action.enterText(firstNameInput, firstName);
        action.enterText(lastNameInput, lastName);
        action.enterText(emailInput, email);
        action.enterText(phoneInput, phone);
        action.click(saveButton);
    }

    public void blankRequiredFields() {
        clickEdit();
        action.enterText(firstNameInput, "");
        action.enterText(lastNameInput, "");
        action.enterText(emailInput, "");
        action.click(saveButton);
    }

    public void enterInvalidEmail(String email) {
        clickEdit();
        action.enterText(emailInput, email);
        action.click(saveButton);
    }

    public void cancelAfterEdit(String firstName) {
        clickEdit();
        action.enterText(firstNameInput, firstName);
        action.click(cancelButton);
    }

    public boolean isValidationDisplayed() {
        return displayed(By.cssSelector("#adm-profile-page .text-red-500, #adm-profile-page [class*='red']"));
    }
}
