package base;

import org.testng.annotations.BeforeClass;
import pages.LoginPage;
import util.PropertyFile;

public class StudentBaseTest extends BaseTest{
    public void loginAsStudent(String username, String password){
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsStudent(username, password);
        log.info("Student logged in successfully");
    }


}
