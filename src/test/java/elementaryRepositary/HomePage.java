package elementaryRepositary;

import base.basePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class HomePage extends basePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}
