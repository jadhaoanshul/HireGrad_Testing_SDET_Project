package com.tests.login;

import com.framework.pages.LoginPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TS02_StudentLoginTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "login", "student", "TS02"},
            description = "TC05 - Verify successful Student login using valid credentials")
    public void TC05_verifySuccessfulStudentLoginUsingValidCredentials() {
        Map<String, String> testData = data("TC05");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsStudent(testData.get("username"), testData.get("password"));
        waitForRoute("/student/home");

        Assert.assertTrue(driver.getCurrentUrl().contains("/student/home"),
                "Student should be redirected to Student Dashboard.");
    }

    @Test(groups = {"regression", "login", "student", "negative", "TS02"},
            description = "TC06 - Verify Student login using invalid credentials")
    public void TC06_verifyStudentLoginUsingInvalidCredentials() {
        Map<String, String> testData = data("TC06");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsStudent(testData.get("invalidUsername"), testData.get("invalidPassword"));
        sleepBriefly();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid student credentials.");
    }

    @Test(groups = {"regression", "login", "student", "negative", "TS02"},
            description = "TC07 - Verify Student login using valid Admin credentials in Student section")
    public void TC07_verifyStudentLoginUsingAdminCredentials() {
        Map<String, String> testData = data("TC07");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsStudent(testData.get("username"), testData.get("password"));
        sleepBriefly();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed when admin credentials are used for Student role.");
    }

    @Test(groups = {"regression", "login", "student", "negative", "TS02"},
            description = "TC08 - Verify Student login leaving fields blank")
    public void TC08_verifyStudentLoginLeavingFieldsBlank() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.selectStudentRole();
        loginPage.submitBlank();

        Assert.assertTrue(loginPage.isValidationDisplayed(),
                "Required validation should be displayed for blank Student login.");
    }

    @Test(groups = {"regression", "login", "student", "negative", "TS02"},
            description = "TC13 - Verify default credentials fail after student updates password")
    public void TC13_verifyDefaultCredentialsFailAfterPasswordUpdate() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsStudent("sri", "1234567");
        sleepBriefly();

        Assert.assertFalse(driver.getCurrentUrl().contains("/student/home"),
                "Default/old credentials should not log the student into the dashboard after a password update.");
    }
}
