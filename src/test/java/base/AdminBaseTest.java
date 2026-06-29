package base;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import util.PropertyFile;

public class AdminBaseTest extends BaseTest{

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setup")
    public void loginAsAdmin(){
        LoginPage loginPage = new LoginPage(driver);
        PropertyFile propertyFile = new PropertyFile();
        String username = propertyFile.getValueProperty("adminUsername");
        String password = propertyFile.getValueProperty("adminPassword");

        loginPage.loginAsPlacementCell(username, password);
        log.info("Admin logged in successfully");
    }

    public void navigateToApplicationMngmntPage(){
        driver.findElement(By.xpath("//a[@id='admin-layout-nav-/admin/applications']//*[name()='svg']")).click();
        log.info("Navigate to Application Management page successfully");
    }

    public void navigateToReportAnalysisPage(){
        driver.findElement(By.xpath("//a[@id='admin-layout-nav-/admin/reports']//*[name()='svg']")).click();
        log.info("Navigate to Report Analysis page successfully");
    }
}
