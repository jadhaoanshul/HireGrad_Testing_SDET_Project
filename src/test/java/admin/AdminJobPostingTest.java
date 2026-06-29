package admin;

import base.AdminBaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.admin.JobPostingManagementPage;
import util.ExcelUtil;
import util.FrameworkConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Listeners(TestListener.class)
public class AdminJobPostingTest extends AdminBaseTest {

    private JobPostingManagementPage jobPostingPage;
    private static final String JOB_LISTING_SHEET = "JobListingData";

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
            dataProvider = "invalidEcpJobListingData",
            description = "TS04_TC18_ECP: Verify invalid equivalence-class job details are rejected")
    public void TC18_verifyInvalidJobDetailsAreRejectedUsingECP(String[] jobData) {
        fillJobListingForm(jobData);
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
            dataProvider = "invalidBvaJobListingData",
            description = "TS04_TC20_BVA: Verify invalid CTC, CGPA and past deadline boundary values are rejected")
    public void TC20_verifyCtcCgpaAndDeadlineBoundaryValidationUsingBVA(String[] jobData) {
        SoftAssert softAssert = new SoftAssert();

        fillJobListingForm(jobData);
        jobPostingPage.clickPublishButton();

        softAssert.assertFalse(
                jobPostingPage.isRedirectedToApplicationManagementPage(),
                jobData[11]);
        softAssert.assertTrue(
                jobPostingPage.isStillOnJobPostingPage(),
                jobData[11] + " and admin should stay on Job Posting page");
        softAssert.assertAll();
    }

    @DataProvider(name = "invalidEcpJobListingData")
    public Object[][] invalidEcpJobListingData() {
        return getJobListingDataByScenarioPrefix("InvalidECP");
    }

    @DataProvider(name = "invalidBvaJobListingData")
    public Object[][] invalidBvaJobListingData() {
        return getJobListingDataByScenarioPrefix("BVA_");
    }

    private Object[][] getJobListingDataByScenarioPrefix(String scenarioPrefix) {
        String[][] allData = new ExcelUtil().getMultipleData(
                FrameworkConstants.JOB_LISTING_DATA_PATH,
                JOB_LISTING_SHEET);

        return Arrays.stream(allData)
                .filter(row -> row[0].startsWith(scenarioPrefix))
                .map(row -> new Object[]{row})
                .toArray(Object[][]::new);
    }

    private void fillJobListingForm(String[] jobData) {
        jobPostingPage.enterCompanyName(jobData[1]);
        jobPostingPage.enterJobTitle(jobData[2]);
        jobPostingPage.enterJobLocation(jobData[3]);
        jobPostingPage.enterCTC(jobData[4]);
        jobPostingPage.selectEmploymentType(jobData[5]);
        jobPostingPage.selectWorkMode(jobData[6]);
        jobPostingPage.enterMinimumCGPA(jobData[7]);
        jobPostingPage.addRequiredSkill(jobData[8]);
        jobPostingPage.enterJobDescription(jobData[9]);
        jobPostingPage.selectApplicationDeadline(formatDeadlineForDateTimeInput(jobData[10]));
    }

    private String formatDeadlineForDateTimeInput(String deadlineFromExcel) {
        String deadline = deadlineFromExcel.trim().replaceAll("\\s+", " ");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        for (String pattern : new String[]{
                "dd-MM-yyyy HH:mm",
                "d-M-yyyy H:mm",
                "yyyy-MM-dd'T'HH:mm",
                "yyyy-MM-dd HH:mm"}) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(deadline, inputFormatter).format(outputFormatter);
            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException(
                "Deadline must be in dd-MM-yyyy HH:mm format. Actual value: " + deadlineFromExcel);
    }
}
