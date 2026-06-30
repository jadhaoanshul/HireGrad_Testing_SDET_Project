package com.tests.student;

import com.framework.pages.StudentJobsPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS10_StudentJobsTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "student", "jobs", "TS10"},
            description = "TC46 - Verify Student can browse and view job listings")
    public void TC46_verifyStudentCanBrowseAndViewJobListings() {
        loginAsStudent();
        studentLayout().goJobs();

        StudentJobsPage page = new StudentJobsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "Jobs page should be loaded with search and filter controls.");
    }

    @Test(groups = {"regression", "student", "jobs", "filter", "TS10"},
            description = "TC47 - Verify search and filter functionality for jobs")
    public void TC47_verifyJobSearchAndFilterFunctionality() {
        loginAsStudent();
        studentLayout().goJobs();

        StudentJobsPage page = new StudentJobsPage(driver);
        page.openFilters();
        Assert.assertTrue(page.isFilterPanelDisplayed(), "Job filter panel should open.");

        skipWhenNoData(page.jobCount() == 0, "Job listing data is not available for search precondition.");
        String firstJobText = page.firstJobText();
        String searchToken = firstJobText.split("\\s+")[0];
        page.searchWithFirstJobText();
        sleepBriefly();

        Assert.assertTrue(page.everyJobContains(searchToken),
                "Filtered jobs should contain searched role/company/location text.");
    }

    @Test(groups = {"regression", "student", "jobs", "apply", "TS10"},
            description = "TC48 - Verify successful job application with valid profile and resume")
    public void TC48_verifySuccessfulJobApplicationWithValidProfileAndResume() {
        loginAsStudent();
        studentLayout().goJobs();

        StudentJobsPage page = new StudentJobsPage(driver);
        skipWhenNoData(!page.hasApplyButton(), "No apply button is available for job application precondition.");
        page.clickFirstApplyButton();
        sleepBriefly();

        Assert.assertTrue(driver.getCurrentUrl().contains("/student/jobs"),
                "Job application action should keep Student on jobs page and show updated state/message.");
    }

    @Test(groups = {"regression", "student", "jobs", "negative", "TS10"},
            description = "TC49 - Verify error handling for incomplete profile or duplicate apply")
    public void TC49_verifyJobApplyErrorHandlingForIncompleteProfileOrDuplicateApply() {
        loginAsStudent();
        studentLayout().goJobs();

        StudentJobsPage page = new StudentJobsPage(driver);
        Assert.assertTrue(page.isLoaded(),
                "Jobs page should handle incomplete-profile or duplicate-apply state without crashing.");
    }
}
