package login;

import base.BaseTest;
import base.StudentBaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import util.ExcelUtil;

import java.time.Duration;

public class LoginTest extends StudentBaseTest {

    public ExcelUtil util=new ExcelUtil();
    public String[][] validStudentData;
    public String[][] invalidStudentData;
    public String[][] invalidAdminData;
    public String[][] validAdminData;
    @DataProvider(name="getStudentValid")
    public String [][] getValidData(){
        return util.getMultipleData("ValidStudent");
    }

    @Test(dataProvider="getStudentValid")
    public void studentValidLogin(String username,String password){
        loginAsStudent(username,password);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/student/home"));
    }
}