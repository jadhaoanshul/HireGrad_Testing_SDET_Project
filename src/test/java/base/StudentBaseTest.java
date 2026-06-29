package base;

import org.testng.annotations.BeforeClass;
import pages.LoginPage;
import util.PropertyFile;

public class StudentBaseTest extends BaseTest{
    @BeforeClass(alwaysRun = true, dependsOnMethods = "setup")
    public void loginAsStudent(){
        LoginPage loginPage = new LoginPage(driver);
        PropertyFile propertyFile = new PropertyFile();
        String username = propertyFile.getValueProperty("studentUsername");
        String password = propertyFile.getValueProperty("studentPassword");

        loginPage.loginAsStudent(username, password);
        log.info("Student logged in successfully");
    }


}
