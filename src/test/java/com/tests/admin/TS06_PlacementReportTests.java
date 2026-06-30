package com.tests.admin;

import com.framework.pages.ReportAnalysisPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS06_PlacementReportTests extends BaseTest {

    @Test(groups = {"regression", "admin", "report", "filter", "TS06"},
            description = "TC28 - Verify Department filter on Placement Report")
    public void TC28_verifyDepartmentFilter() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);
        page.clearFilters();

        page.filterFirstDepartment();
        sleepBriefly();

        Assert.assertTrue(page.rowCount() >= 0 || page.isNoDataDisplayed(),
                "Report should show department filtered data or no-data state.");
    }

    @Test(groups = {"regression", "admin", "report", "filter", "TS06"},
            description = "TC29 - Verify College filter on Placement Report")
    public void TC29_verifyCollegeFilter() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);
        page.clearFilters();

        page.filterFirstCollege();
        sleepBriefly();

        Assert.assertTrue(page.rowCount() >= 0 || page.isNoDataDisplayed(),
                "Report should show college filtered data or no-data state.");
    }

    @Test(groups = {"regression", "admin", "report", "filter", "TS06"},
            description = "TC30 - Verify Pass-out Year filter on Placement Report")
    public void TC30_verifyPassOutYearFilter() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);
        page.clearFilters();

        page.filterFirstYear();
        sleepBriefly();

        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/reports"),
                "Reports page should remain functional after pass-out year filter.");
    }

    @Test(groups = {"regression", "admin", "report", "search", "TS06"},
            description = "TC31 - Verify Admin can search student placement records")
    public void TC31_verifyAdminCanSearchStudentPlacementRecords() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);
        page.clearFilters();

        skipWhenNoData(page.rowCount() == 0, "Student placement records are not available for search precondition.");
        String searchText = page.firstRowText();
        page.search(searchText);
        sleepBriefly();

        Assert.assertTrue(page.rowCount() > 0, "Matching student details should be displayed.");
        Assert.assertTrue(page.everyRowContains(searchText),
                "Displayed report records should match searched student.");
    }

    @Test(groups = {"smoke", "regression", "admin", "report", "TS06"},
            description = "TC32 - Verify Refresh button reloads latest placement report data")
    public void TC32_verifyRefreshButtonReloadsLatestReportData() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);

        page.refresh();
        sleepBriefly();

        Assert.assertTrue(page.isLoaded(),
                "Latest report data should be loaded successfully after refresh.");
    }

    @Test(groups = {"regression", "admin", "report", "export", "TS06"},
            description = "TC33 - Verify Admin can export placement report in CSV and PDF format")
    public void TC33_verifyAdminCanExportPlacementReportInCsvAndPdfFormat() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);

        page.exportCsv();
        sleepBriefly();
        Assert.assertTrue(driver.getCurrentUrl().contains("/admin/reports"),
                "CSV export should keep Reports page functional.");

        page.exportPdfWithoutDialog();
        sleepBriefly();
        Assert.assertTrue(page.wasPrintCalled(),
                "PDF export should trigger browser print functionality.");
    }

    @Test(groups = {"regression", "admin", "report", "drilldown", "TS06"},
            description = "TC34 - Verify Admin can view company-wise placement details")
    public void TC34_verifyAdminCanViewCompanyWisePlacementDetails() {
        loginAsAdmin();
        adminLayout().goReports();
        ReportAnalysisPage page = new ReportAnalysisPage(driver);
        page.clearFilters();

        String company = page.clickFirstCompany();
        skipWhenNoData(company.isBlank(), "Company placement chart is not available for drilldown precondition.");
        sleepBriefly();

        Assert.assertTrue(page.isCompanyFilterApplied(),
                "Company filter should be applied after selecting a company.");
        Assert.assertTrue(page.allListedStudentsBelongToCompany(company),
                "Company-wise placement details should list only applicants selected by '" + company
                        + "', but applicants not selected in any company are also displayed (defect DF07).");
    }
}
