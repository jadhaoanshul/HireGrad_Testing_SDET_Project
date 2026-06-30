package com.tests.admin;

import com.framework.pages.JobPostingPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS04_AdminJobPostingTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "admin", "jobposting", "TS04"},
            description = "TC17 - Verify Admin Job Posting page UI components")
    public void TC17_verifyAdminJobPostingPageUiComponents() {
        loginAsAdmin();
        adminLayout().goJobs();

        Assert.assertTrue(new JobPostingPage(driver).isLoaded(),
                "Job Posting page fields, sections and buttons should be displayed.");
    }

    @Test(groups = {"regression", "admin", "jobposting", "negative", "TS04"},
            description = "TC18 - Verify Admin cannot publish job with invalid job details")
    public void TC18_verifyAdminCannotPublishJobWithInvalidJobDetails() {
        loginAsAdmin();
        adminLayout().goJobs();

        JobPostingPage jobPostingPage = new JobPostingPage(driver);
        jobPostingPage.fillInvalidJobData();
        jobPostingPage.publish();
        sleepBriefly();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(jobPostingPage.isValidationDisplayed()
                        || (currentUrl.contains("/admin/jobs") && !currentUrl.contains("/admin/applications")),
                "Invalid job details should be rejected with a validation message and the Admin should remain on "
                        + "the Job Posting page, but the job was published and Admin was redirected to Application "
                        + "Management (defects DF02-DF06).");
    }

    @Test(groups = {"regression", "admin", "jobposting", "negative", "TS04"},
            description = "TC19 - Verify validation messages when mandatory fields are blank")
    public void TC19_verifyMandatoryFieldValidationMessages() {
        loginAsAdmin();
        adminLayout().goJobs();

        JobPostingPage jobPostingPage = new JobPostingPage(driver);
        jobPostingPage.publish();
        sleepBriefly();

        Assert.assertTrue(jobPostingPage.isValidationDisplayed(),
                "Mandatory field validation should be displayed.");
    }

    @Test(groups = {"regression", "admin", "jobposting", "negative", "TS04"},
            description = "TC20 - Verify validation for invalid CTC, CGPA and past deadline")
    public void TC20_verifyBoundaryValidationForCtcCgpaAndPastDeadline() {
        loginAsAdmin();
        adminLayout().goJobs();

        JobPostingPage jobPostingPage = new JobPostingPage(driver);
        jobPostingPage.fillInvalidCtcCgpaAndPastDeadline();
        jobPostingPage.publish();
        sleepBriefly();

        Assert.assertTrue(jobPostingPage.isValidationDisplayed() || driver.getCurrentUrl().contains("/admin/jobs"),
                "Invalid CTC, CGPA and past deadline should block publish.");
    }
}
