package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class HomePage extends BasePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}
