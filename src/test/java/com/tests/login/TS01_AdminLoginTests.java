package com.tests.login;

import com.framework.pages.LoginPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TS01_AdminLoginTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "login", "admin", "TS01"},
            description = "TC01 - Verify successful Admin login using valid credentials")
    public void TC01_verifySuccessfulAdminLoginUsingValidCredentials() {
        Map<String, String> testData = data("TC01");
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoaded(), "Login page should be loaded.");

        loginPage.loginAsAdmin(testData.get("username"), testData.get("password"));
        waitForRoute("/admin/home");

        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/home"),
                "Admin should be redirected to Admin Dashboard.");
    }

    @Test(groups = {"regression", "login", "admin", "negative", "TS01"},
            description = "TC02 - Verify Admin login using invalid credentials")
    public void TC02_verifyAdminLoginUsingInvalidCredentials() {
        Map<String, String> testData = data("TC02");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsAdmin(testData.get("invalidUsername"), testData.get("invalidPassword"));
        sleepBriefly();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed for invalid admin credentials.");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "Admin should remain on login page for invalid credentials.");
    }

    @Test(groups = {"regression", "login", "admin", "negative", "TS01"},
            description = "TC03 - Verify Admin login using valid student credentials in Admin section")
    public void TC03_verifyAdminLoginUsingStudentCredentials() {
        Map<String, String> testData = data("TC03");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsAdmin(testData.get("username"), testData.get("password"));
        sleepBriefly();

        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message should be displayed when student credentials are used for Admin role.");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "User should not enter Admin area with student credentials.");
    }

    @Test(groups = {"regression", "login", "admin", "negative", "TS01"},
            description = "TC04 - Verify Admin login leaving fields blank")
    public void TC04_verifyAdminLoginLeavingFieldsBlank() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.selectAdminRole();
        loginPage.submitBlank();

        Assert.assertTrue(loginPage.isValidationDisplayed(),
                "Required validation should be displayed for blank Admin login.");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"),
                "Admin should remain on login page when mandatory fields are blank.");
    }
}
