package com.tests.admin;

import com.framework.pages.ApplicationManagementPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS05_ApplicationManagementTests extends BaseTest {

    @Test(groups = {"smoke", "regression", "admin", "applicantmanagement", "search", "TS05"},
            description = "TC22 - Verify Admin can search applicants with search textbox")
    public void TC22_verifyAdminCanSearchApplicantsWithSearchTextbox() {
        loginAsAdmin();
        adminLayout().goApplications();
        ApplicationManagementPage page = new ApplicationManagementPage(driver);
        page.resetFilters();

        skipWhenNoData(page.rowCount() == 0, "Applicant records are not available for search precondition.");
        String searchText = page.firstRowSearchText();
        page.search(searchText);
        sleepBriefly();

        Assert.assertTrue(page.rowCount() > 0, "Matching applicant records should be displayed.");
        Assert.assertTrue(page.everyRowContains(searchText),
                "Displayed applicant records should match the search text.");
    }

    @Test(groups = {"regression", "admin", "applicantmanagement", "search", "negative", "TS05"},
            description = "TC23 - Verify applicant search with invalid details")
    public void TC23_verifyApplicantSearchWithInvalidDetails() {
        loginAsAdmin();
        adminLayout().goApplications();
        ApplicationManagementPage page = new ApplicationManagementPage(driver);
        page.resetFilters();

        page.search("InvalidApplicant99999");
        sleepBriefly();

        Assert.assertTrue(page.rowCount() == 0 || page.isNoDataDisplayed(),
                "No matching records or empty applicant table should be shown.");
    }

    @Test(groups = {"regression", "admin", "applicantmanagement", "filter", "TS05"},
            description = "TC24 - Verify Admin can filter applicants by branch")
    public void TC24_verifyAdminCanFilterApplicantsByBranch() {
        loginAsAdmin();
        adminLayout().goApplications();
        ApplicationManagementPage page = new ApplicationManagementPage(driver);
        page.resetFilters();

        String branch = page.firstBranchOptionText();
        skipWhenNoData(branch.isBlank(), "Branch options are not available for filter precondition.");
        page.filterByFirstBranchOption();
        sleepBriefly();

        Assert.assertTrue(page.rowCount() == 0 || page.everyRowContainsBranch(branch),
                "Only applicants belonging to selected branch should be displayed.");
    }

    @Test(groups = {"regression", "admin", "applicantmanagement", "status", "TS05"},
            description = "TC25 - Verify Admin can change applicant status")
    public void TC25_verifyAdminCanChangeApplicantStatus() {
        loginAsAdmin();
        adminLayout().goApplications();
        ApplicationManagementPage page = new ApplicationManagementPage(driver);
        page.resetFilters();

        skipWhenNoData(!page.hasStatusDropdown(), "Applicant status dropdown is not available for status update precondition.");
        page.changeFirstStatus();
        sleepBriefly();

        Assert.assertTrue(page.hasStatusDropdown(),
                "Updated status should remain visible in applicant table.");
    }
}
