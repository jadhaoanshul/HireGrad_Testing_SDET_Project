package com.tests.student;

import com.framework.pages.StudentProfilePage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class TS09_StudentProfileTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "student", "profile", "TS09"},
            description = "TC43 - Verify Student Profile page UI fields")
    public void TC43_verifyStudentProfileUiFields() {
        loginAsStudent();
        studentLayout().goProfile();

        Assert.assertTrue(new StudentProfilePage(driver).isLoaded(),
                "Student Profile page fields and sections should be displayed.");
    }

    @Test(groups = {"regression", "student", "profile", "TS09"},
            description = "TC44 - Verify Student can save valid profile and resume details")
    public void TC44_verifyStudentCanSaveValidProfileDetails() {
        Map<String, String> testData = data("TC44");
        loginAsStudent();
        studentLayout().goProfile();

        StudentProfilePage page = new StudentProfilePage(driver);
        page.updateValidProfile(
                testData.get("firstName"),
                testData.get("lastName"),
                testData.get("email"),
                testData.get("phone"));
        sleepBriefly();

        Assert.assertTrue(driver.getCurrentUrl().contains("/student/profile"),
                "Student Profile should remain loaded after saving valid details.");
    }

    @Test(groups = {"regression", "student", "profile", "negative", "TS09"},
            description = "TC45 - Verify Student Profile error handling for invalid input formats")
    public void TC45_verifyStudentProfileInvalidInputHandling() {
        loginAsStudent();
        studentLayout().goProfile();

        StudentProfilePage page = new StudentProfilePage(driver);
        page.enterInvalidProfileData();
        sleepBriefly();

        Assert.assertTrue(page.isValidationDisplayed(),
                "Invalid student profile input should display validation error.");
    }
}
