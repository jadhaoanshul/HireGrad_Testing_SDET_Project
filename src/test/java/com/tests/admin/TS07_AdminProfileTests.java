package com.tests.admin;

import com.framework.pages.AdminProfilePage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TS07_AdminProfileTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "admin", "profile", "TS07"},
            description = "TC36 - Verify Admin Profile page loads successfully")
    public void TC36_verifyAdminProfilePageLoadsSuccessfully() {
        loginAsAdmin();
        adminLayout().goProfile();

        Assert.assertTrue(new AdminProfilePage(driver).isLoaded(),
                "Admin Profile page and profile elements should be displayed.");
    }

    @Test(groups = {"regression", "admin", "profile", "TS07"},
            description = "TC37 - Verify Admin can update required profile details")
    public void TC37_verifyAdminCanUpdateRequiredProfileDetails() {
        Map<String, String> testData = data("TC37");
        loginAsAdmin();
        adminLayout().goProfile();

        AdminProfilePage page = new AdminProfilePage(driver);
        page.updateRequiredFields(
                testData.get("firstName"),
                testData.get("lastName"),
                testData.get("email"),
                testData.get("phone"));
        sleepBriefly();

        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/profile"),
                "Admin profile should remain loaded after saving valid profile details.");
    }

    @Test(groups = {"regression", "admin", "profile", "negative", "TS07"},
            description = "TC38 - Verify validation errors when Admin profile required fields are blank")
    public void TC38_verifyValidationWhenRequiredProfileFieldsAreBlank() {
        loginAsAdmin();
        adminLayout().goProfile();

        AdminProfilePage page = new AdminProfilePage(driver);
        page.blankRequiredFields();
        sleepBriefly();

        Assert.assertTrue(page.isValidationDisplayed(),
                "Validation errors should be displayed for blank required Admin profile fields.");
    }

    @Test(groups = {"regression", "admin", "profile", "negative", "TS07"},
            description = "TC39 - Verify invalid institute email is rejected")
    public void TC39_verifyInvalidInstituteEmailIsRejected() {
        Map<String, String> testData = data("TC39");
        loginAsAdmin();
        adminLayout().goProfile();

        AdminProfilePage page = new AdminProfilePage(driver);
        page.enterInvalidEmail(testData.get("email"));
        sleepBriefly();

        Assert.assertTrue(page.isValidationDisplayed(),
                "Invalid institute email should show validation error.");
    }

    @Test(groups = {"regression", "admin", "profile", "TS07"},
            description = "TC40 - Verify Cancel button functionality on Admin Profile page")
    public void TC40_verifyCancelButtonFunctionalityOnProfilePage() {
        Map<String, String> testData = data("TC40");
        loginAsAdmin();
        adminLayout().goProfile();

        AdminProfilePage page = new AdminProfilePage(driver);
        page.cancelAfterEdit(testData.get("firstName"));
        sleepBriefly();

        Assert.assertTrue(page.isLoaded(),
                "Admin Profile page should remain loaded after clicking Cancel.");
    }
}
