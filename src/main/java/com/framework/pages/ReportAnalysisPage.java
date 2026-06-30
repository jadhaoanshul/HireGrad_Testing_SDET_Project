package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ReportAnalysisPage extends BasePage {
    @FindBy(id = "report-refresh-btn")
    private WebElement refreshButton;
    @FindBy(id = "report-export-csv-btn")
    private WebElement csvButton;
    @FindBy(id = "report-export-pdf-btn")
    private WebElement pdfButton;
    @FindBy(id = "report-search-input")
    private WebElement searchInput;

    private final By page = By.id("report-page");
    private final By filterBar = By.id("report-filter-bar");
    private final By kpiCards = By.cssSelector("[id^='report-kpi-card-']");
    private final By rows = By.cssSelector("#report-per-student-table tbody tr");
    private final By noData = By.xpath("//section[@id='report-per-student-section']//p[contains(.,'No')]");
    private final By companyBars = By.cssSelector("[id^='report-company-bar-']");

    public ReportAnalysisPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForReportReady();
        return displayed(page)
                && displayed(filterBar)
                && displayed(By.id("report-refresh-btn"))
                && displayed(By.id("report-export-csv-btn"))
                && displayed(By.id("report-export-pdf-btn"))
                && count(kpiCards) > 0;
    }

    public void clearFilters() {
        waitForReportReady();
        if (present(By.id("report-clear-filters-btn"))) {
            action.click(By.id("report-clear-filters-btn"));
        }
    }

    public void filterFirstDepartment() {
        waitForReportReady();
        action.selectFirstRealOption(By.id("report-dept-filter"));
    }

    public void filterFirstCollege() {
        waitForReportReady();
        action.selectFirstRealOption(By.id("report-college-filter"));
    }

    public void filterFirstYear() {
        waitForReportReady();
        action.selectFirstRealOption(By.id("report-year-filter"));
    }

    public void search(String value) {
        waitForReportReady();
        action.enterText(searchInput, value);
    }

    public String firstRowText() {
        List<WebElement> tableRows = all(rows);
        return tableRows.isEmpty() ? "" : tableRows.get(0).getText().split("\\R")[0].trim();
    }

    public int rowCount() {
        return count(rows);
    }

    public boolean isNoDataDisplayed() {
        return displayed(noData);
    }

    public boolean everyRowContains(String value) {
        return everyRowContains(rows, value);
    }

    public void refresh() {
        waitForReportReady();
        action.click(refreshButton);
    }

    public void exportCsv() {
        waitForReportReady();
        action.click(csvButton);
    }

    public void exportPdfWithoutDialog() {
        waitForReportReady();
        js.execute("window.__hireGradPrintCalled=false; window.print=function(){window.__hireGradPrintCalled=true;};");
        action.click(pdfButton);
    }

    public boolean wasPrintCalled() {
        Object result = js.execute("return window.__hireGradPrintCalled === true;");
        return Boolean.TRUE.equals(result);
    }

    public String clickFirstCompany() {
        waitForReportReady();
        List<WebElement> companies = all(companyBars);
        if (companies.isEmpty()) {
            return "";
        }
        WebElement firstCompany = companies.get(0);
        String company = companyLabelOf(firstCompany);
        firstCompany.click();
        return company;
    }

    private String companyLabelOf(WebElement companyBar) {
        String id = companyBar.getAttribute("id");
        String prefix = "report-company-bar-";
        if (id != null && id.startsWith(prefix)) {
            return id.substring(prefix.length()).trim();
        }
        return companyBar.getText().split("\\R")[0].trim();
    }

    public boolean isCompanyFilterApplied() {
        return present(By.id("report-clear-company-btn"));
    }

    /**
     * After a company is selected, the per-student placement table must list ONLY the
     * applicants who have an offer from that company (the company appears in their
     * "Offers from" column). If any listed student does not belong to the selected
     * company, the company filter is leaking unselected applicants (defect DF07).
     */
    public boolean allListedStudentsBelongToCompany(String company) {
        return everyRowContains(rows, company);
    }

    public int perStudentRowCount() {
        return count(rows);
    }

    private void waitForReportReady() {
        action.waitForAny(filterBar, By.xpath("//p[contains(.,'Could not load') or contains(.,'No students match')]"));
    }
}
