package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.ActionUtil;

public class LoginPage extends BasePage {

    ActionUtil actionUtil;
    private static final By STUDENT_TAB = By.id("login-role-student-tab");
    private static final By ADMIN_TAB = By.id("login-role-admin-tab");
    private static final By USERNAME_INPUT = By.id("login-username-input");
    private static final By PASSWORD_INPUT = By.id("login-password-input");
    private static final By SIGN_IN_BUTTON = By.id("login-submit-btn");

    public LoginPage(WebDriver driver){
        super(driver);
        actionUtil = new ActionUtil(driver);
    }

    @FindBy(id="login-role-student-tab")
    WebElement studentbtn;

    @FindBy(id="login-role-admin-tab")
    WebElement placementcellBtn;

    @FindBy(id="login-username-input")
    WebElement username;

    @FindBy(id="login-password-input")
    WebElement password;

    @FindBy(id="login-submit-btn")
    WebElement signinBtn;

    public void loginAsStudent(String user,String pass){
        clickFresh(STUDENT_TAB);
        waitAfterRoleSelection();
        setInputValue(USERNAME_INPUT, user);
        setInputValue(PASSWORD_INPUT, pass);
        clickFresh(SIGN_IN_BUTTON);
    }
    public void loginAsPlacementCell(String user,String pass){
        clickFresh(ADMIN_TAB);
        waitAfterRoleSelection();
        setInputValue(USERNAME_INPUT, user);
        setInputValue(PASSWORD_INPUT, pass);
        clickFresh(SIGN_IN_BUTTON);
    }

    private void waitAfterRoleSelection() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private void setInputValue(By locator, String value) {
        RuntimeException lastException = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
                element.click();
                element.clear();
                element.sendKeys(value);
                return;
            } catch (StaleElementReferenceException exception) {
                lastException = exception;
            }
        }
        throw new IllegalStateException("Unable to enter value for locator: " + locator, lastException);
    }

    private void clickFresh(By locator) {
        RuntimeException lastException = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
                element.click();
                return;
            } catch (StaleElementReferenceException exception) {
                lastException = exception;
            }
        }
        throw new IllegalStateException("Unable to click locator: " + locator, lastException);
    }

}
