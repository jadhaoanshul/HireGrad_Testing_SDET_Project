package pages.admin;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver; import org.openqa.selenium.WebElement; import org.openqa.selenium.support.FindBy;
import util.ActionUtil;

import java.util.List;

public class ReportAnalysisPage extends BasePage {

    @FindBy(id = "report-refresh-btn")
    public WebElement refreshBtn;

    @FindBy(id = "report-export-csv-btn")
    public WebElement csvExportBtn;

    @FindBy(id = "report-export-pdf-btn")
    public WebElement pdfExportBtn;

    @FindBy(id = "report-dept-filter")
    public WebElement departmentFilter;

    @FindBy(id = "report-college-filter")
    public WebElement collegeFilter;

    @FindBy(id = "report-year-filter")
    public WebElement yearFilter;

    @FindBy(id = "report-search-input")
    public WebElement studentSearchTextbox;

    @FindBy(xpath = "//div[@id='report-company-card']//span[text()='zoho']")
    public WebElement companyName;

    @FindBy(xpath = "//table[@id='report-per-student-table']//tbody/tr")
    public List<WebElement> reportTableRows;

    @FindBy(xpath = "//section[@id='report-per-student-section']//p[contains(text(),'No students match')]")
    public WebElement emptyStateMsg;

    ActionUtil actionUtil;

    public ReportAnalysisPage(WebDriver driver) {
        super(driver);
        actionUtil = new ActionUtil(driver);
    }

    public void clickRefresh() {
        actionUtil.click(refreshBtn);
    }

    public void clickCSVExport() {
        actionUtil.click(csvExportBtn);
    }

    public void clickPDFExport() {
        actionUtil.click(pdfExportBtn);
    }

    public void filterByDepartment(String dept) {
        actionUtil.selectByVisibleText(departmentFilter, dept);
    }

    public void filterByCollege(String college) {
        actionUtil.selectByVisibleText(collegeFilter, college);
    }

    public void filterByYear(String year) {
        actionUtil.selectByVisibleText(yearFilter, year);
    }

    public void searchStudent(String name) {
        actionUtil.enterText(studentSearchTextbox, name);
    }

    public int getReportRowCount() {
        return reportTableRows.size();
    }

    public boolean isEmptyStateMsgDisplayed() {
        try {
            return actionUtil.isDisplayed(emptyStateMsg);
        } catch (Exception e) {
            return false;
        }
    }

    public String clickTargetCompanyElement() {
        String targetText = companyName.getText().trim();
        actionUtil.click(companyName);
        return targetText;
    }

    public boolean verifyTableFilteredByCompanyText(String expectedCompanyText) {
        if (reportTableRows.isEmpty()) {
            return true;
        }

        for (WebElement row : reportTableRows) {
            WebElement offersFromCell = row.findElement(By.xpath("./td[7]"));
            String offersText = offersFromCell.getText().toLowerCase().trim();

            if (!offersText.contains(expectedCompanyText.toLowerCase().trim())) {
                return false;
            }
        }
        return true;
    }
}