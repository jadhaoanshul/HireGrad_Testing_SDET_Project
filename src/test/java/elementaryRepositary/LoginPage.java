package elementaryRepositary;

import base.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends basePage {

    public LoginPage(WebDriver driver){
        super(driver);
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

    public void selectPlacementCell(){
        placementcellBtn.click();
    }

    public void selectStudent(){
        studentbtn.click();
    }

    public void enterUsername(String userName){
        username.clear();
        username.sendKeys(userName);
    }

    public void enterPassword(String pass){
        password.clear();
        password.sendKeys(pass);
    }

    public void clickOnSigninBtn(){
        signinBtn.click();
    }

    public void loginAsStudent(String user,String pass){
        selectStudent();
        enterUsername(user);
        enterPassword(pass);
        clickOnSigninBtn();

    }
    public void loginAsPlacementCell(String user,String pass){
        selectPlacementCell();
        enterUsername(user);
        enterPassword(pass);
        clickOnSigninBtn();

    }


}
