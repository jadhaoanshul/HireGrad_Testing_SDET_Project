package login;

import base.StudentBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.ExcelUtil;

import java.time.Duration;

public class StudentLoginTest extends StudentBaseTest {

    public ExcelUtil util=new ExcelUtil();
    @DataProvider(name="getStudentValid")
    public String [][] getValidData(){

        return util.getMultipleData("ValidStudent");
    }

    @DataProvider(name="getStudentInvalid")
    public String [][] getInvalidData(){

        return util.getMultipleData("InvalidStudent");
    }

    @Test(dataProvider="getStudentInvalid")
    public void studentInvalidLogin(String username,String password){
        loginAsStudent(username,password);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@role='alert'])[1]")));
        refreshApplication();
    }

    @Test(dataProvider="getStudentValid")
    public void studentValidLogin(String username,String password){
        loginAsStudent(username,password);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/student/home"));
        refreshApplication();
    }
}