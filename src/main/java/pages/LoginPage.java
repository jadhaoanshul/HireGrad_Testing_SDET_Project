package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.ActionUtil;

public class LoginPage extends BasePage {

    ActionUtil actionUtil;

    public LoginPage(WebDriver driver){
        super(driver);
        actionUtil = new ActionUtil(driver);
    }

    @FindBy(id="login-role-student-tab")
    WebElement studentbtn;

    @FindBy(id="login-role-admin-tab")
    WebElement placementcellBtn;

    @FindBy(id="login-username-input")
    WebElement username;

    @FindBy(id="login-password-input")
    WebElement password;

    @FindBy(id="login-submit-btn")
    WebElement signinBtn;

    public void loginAsStudent(String user,String pass){
        actionUtil.click(studentbtn);
        actionUtil.enterText(username,user);
        actionUtil.enterText(password,pass);
        actionUtil.click(signinBtn);
    }
    public void loginAsPlacementCell(String user,String pass){
        actionUtil.click(placementcellBtn);
        actionUtil.enterText(username,user);
        actionUtil.enterText(password,pass);
        actionUtil.click(signinBtn);
    }


}
