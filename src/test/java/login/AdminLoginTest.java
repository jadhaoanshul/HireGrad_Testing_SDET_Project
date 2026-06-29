package login;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import util.ExcelUtil;

import java.time.Duration;

public class AdminLoginTest extends BaseTest {
    public ExcelUtil util=new ExcelUtil();
    public LoginPage login;
    @DataProvider(name="getAdminValid")
    public String [][] getValidData(){
        return util.getMultipleData("ValidAdmin");
    }

    @DataProvider(name="getAdminInvalid")
    public String [][] getInvalidData(){

        return util.getMultipleData("InvalidAdmin");
    }

    @Test(dataProvider="getAdminInvalid")
    public void studentInvalidLogin(String username,String password){
        login = new LoginPage(driver);
        login.loginAsPlacementCell(username,password);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='alert']")));
        }
        catch(Exception e){
            log.info("Validation Failed");
        }
        refreshApplication();
    }

    @Test(dataProvider="getAdminValid")
    public void studentValidLogin(String username,String password){
        login = new LoginPage(driver);
        login.loginAsPlacementCell(username,password);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        try{
            wait.until(ExpectedConditions.urlContains("/admin/home"));
        } catch (Exception e) {
            log.info("Admin login successful");
        }

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/admin/home"),
                "User not navigated to admin home"
        );

        refreshApplication();
    }
}
