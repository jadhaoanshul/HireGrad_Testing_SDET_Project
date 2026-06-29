package applicationmanagement;

import base.AdminBaseTest;
import org.testng.annotations.Test;
import pages.admin.ApplicationManagementPage;

public class TC_022_ToSearchApplicantsWithValidName extends AdminBaseTest {

    @Test
    public void searchVerify() throws InterruptedException {
        navigateToApplicationMngmntPage();
        ApplicationManagementPage page = new ApplicationManagementPage(driver);
        page.searchApplicant("Bhanu");
        Thread.sleep(5000);

    }
}
