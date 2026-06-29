package admin;

import base.AdminBaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.admin.JobPostingManagementPage;

import java.time.LocalDate;

@Listeners(TestListener.class)
public class AdminJobPostingTest extends AdminBaseTest {

    private JobPostingManagementPage jobPostingPage;

    @BeforeMethod(alwaysRun = true)
    public void openJobPostingPage() {
        navigateToJobPostingPage();
        jobPostingPage = new JobPostingManagementPage(driver);
        Assert.assertTrue(
                jobPostingPage.isJobPostingPageLoaded(),
                "Admin Job Posting page should be loaded on /admin/jobs route");
    }

    @Test(
            priority = 1,
            description = "TS04_TC17: Verify all Admin Job Posting UI components are visible")
    public void TC17_verifyAdminJobPostingUIComponentsAreVisible() {
        Assert.assertTrue(
                jobPostingPage.isJobPostingHeadingDisplayed(),
                "Job Posting heading should be visible");

        Assert.assertTrue(
                jobPostingPage.areAllJobPostingFieldsDisplayed(),
                "All job posting fields, buttons and selection controls should be displayed");
    }

    @Test(
            priority = 2,
            description = "TS04_TC18_ECP: Verify invalid equivalence-class job details are rejected")
    public void TC18_verifyInvalidJobDetailsAreRejectedUsingECP() {
        jobPostingPage.enterCompanyName("1234");
        jobPostingPage.enterJobTitle("124");
        jobPostingPage.enterJobLocation("124");
        jobPostingPage.enterCTC("600000");
        jobPostingPage.enterMinimumCGPA("7");
        jobPostingPage.addRequiredSkill("1234");
        jobPostingPage.enterJobDescription("Automation test job description");
        jobPostingPage.selectApplicationDeadline(LocalDate.now().plusDays(10).toString());
        jobPostingPage.clickPublishButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(
                jobPostingPage.isRedirectedToApplicationManagementPage(),
                "Invalid job details should not publish and redirect to Application Management");
        softAssert.assertTrue(
                jobPostingPage.isStillOnJobPostingPage(),
                "Admin should remain on Job Posting page when invalid ECP data is entered");
        softAssert.assertTrue(
                jobPostingPage.isCompanyNameRequiredMessageDisplayed(),
                "Validation error should display for numeric-only company name");
        softAssert.assertTrue(
                jobPostingPage.isJobTitleRequiredMessageDisplayed(),
                "Validation error should display for numeric-only job title");
        softAssert.assertTrue(
                jobPostingPage.isLocationRequiredMessageDisplayed(),
                "Validation error should display for numeric-only location");
        softAssert.assertTrue(
                jobPostingPage.isRequiredSkillsRequiredMessageDisplayed(),
                "Validation error should display for numeric-only required skill");
        softAssert.assertAll();
    }

    @Test(
            priority = 3,
            description = "TS04_TC19: Verify validation messages when mandatory fields are blank")
    public void TC19_verifyMandatoryFieldValidationMessagesForBlankForm() {
        jobPostingPage.clickPublishButton();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(
                jobPostingPage.isCompanyNameRequiredMessageDisplayed(),
                "Company name required validation should display");
        softAssert.assertTrue(
                jobPostingPage.isJobTitleRequiredMessageDisplayed(),
                "Job title required validation should display");
        softAssert.assertTrue(
                jobPostingPage.isLocationRequiredMessageDisplayed(),
                "Location required validation should display");
        softAssert.assertTrue(
                jobPostingPage.isCtcRequiredMessageDisplayed(),
                "CTC required validation should display");
        softAssert.assertTrue(
                jobPostingPage.isRequiredSkillsRequiredMessageDisplayed(),
                "Required skills validation should display");
        softAssert.assertTrue(
                jobPostingPage.isJobDescriptionRequiredMessageDisplayed(),
                "Job description required validation should display");
        softAssert.assertTrue(
                jobPostingPage.isApplicationDeadlineRequiredMessageDisplayed(),
                "Application deadline required validation should display");
        softAssert.assertAll();
    }

    @Test(
            priority = 4,
            description = "TS04_TC20_BVA: Verify invalid CTC, CGPA and past deadline boundary values are rejected")
    public void TC20_verifyCtcCgpaAndDeadlineBoundaryValidationUsingBVA() {
        SoftAssert softAssert = new SoftAssert();

        verifyInvalidBoundaryData("0", "7", LocalDate.now().plusDays(10).toString(), "Zero CTC should be rejected", softAssert);
        verifyInvalidBoundaryData("-1", "7", LocalDate.now().plusDays(10).toString(), "Negative CTC should be rejected", softAssert);
        verifyInvalidBoundaryData("600000", "-1", LocalDate.now().plusDays(10).toString(), "CGPA below 0 should be rejected", softAssert);
        verifyInvalidBoundaryData("600000", "11", LocalDate.now().plusDays(10).toString(), "CGPA above 10 should be rejected", softAssert);
        verifyInvalidBoundaryData("600000", "7", LocalDate.now().minusDays(1).toString(), "Past deadline should be rejected", softAssert);

        softAssert.assertAll();
    }

    private void verifyInvalidBoundaryData(
            String ctc,
            String cgpa,
            String deadline,
            String assertionMessage,
            SoftAssert softAssert) {

        navigateToJobPostingPage();
        jobPostingPage = new JobPostingManagementPage(driver);
        Assert.assertTrue(jobPostingPage.isJobPostingPageLoaded(), "Job Posting page should be loaded before BVA input");

        jobPostingPage.enterCompanyName("Cognizant");
        jobPostingPage.enterJobTitle("Software Engineer");
        jobPostingPage.enterJobLocation("Pune");
        jobPostingPage.enterCTC(ctc);
        jobPostingPage.enterMinimumCGPA(cgpa);
        jobPostingPage.addRequiredSkill("Java");
        jobPostingPage.enterJobDescription("Automation boundary validation job description");
        jobPostingPage.selectApplicationDeadline(deadline);
        jobPostingPage.clickPublishButton();

        softAssert.assertFalse(
                jobPostingPage.isRedirectedToApplicationManagementPage(),
                assertionMessage);
        softAssert.assertTrue(
                jobPostingPage.isStillOnJobPostingPage(),
                assertionMessage + " and admin should stay on Job Posting page");
    }
}
