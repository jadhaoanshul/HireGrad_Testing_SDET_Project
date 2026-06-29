package pages.admin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.ActionUtil;

import java.util.List;

public class ApplicationManagementPage extends BasePage {

    ActionUtil actionUtil;

    @FindBy(id = "appmgmt-search-input")
    public WebElement searchTextBox;

    @FindBy(id = "appmgmt-branch-filter")
    public WebElement branchFilterDropdown;

    @FindBy(id = "appmgmt-reset-btn")
    public WebElement resetBtn;

    @FindBy(xpath = "//tbody[@class='divide-y divide-slate-100 dark:divide-slate-800']/tr")
    public List<WebElement> applicantRows;

    @FindBy(xpath = "//p[contains(text(), 'No applications received')]")
    public WebElement noApplicantMsg;

    @FindBy(xpath = "//table[@id='appmgmt-applicants-table']//tbody//select")
    public List<WebElement> statusDropdowns;

    public ApplicationManagementPage(WebDriver driver) {
        super(driver);
        actionUtil = new ActionUtil(driver);
    }

    public void searchApplicant(String name){
        actionUtil.enterText(searchTextBox, name);
    }

    public void filterByBranch(String branch){
        actionUtil.selectByVisibleText(branchFilterDropdown, branch);
    }

    public void clickResetBtn(){
        actionUtil.click(resetBtn);
    }

    public int getNumberOfApplicantRows(){
        return applicantRows.size();
    }

    public boolean isNoApplicantMsgDisplayed(){
        try {
            return actionUtil.isDisplayed(noApplicantMsg);
        }
        catch (Exception e) {
            return false;
        }
    }

    public void changeStatusDropdown(int rowIndex, String status){
        actionUtil.selectByVisibleText(statusDropdowns.get(rowIndex), status);
    }


}
