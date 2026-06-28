package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    WebDriver driver;
    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

        log.info("WebDriver Initialized for: {}", this.getClass().getSimpleName());
    }
}
