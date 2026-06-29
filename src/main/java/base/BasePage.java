package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.FrameworkConstants;

import java.time.Duration;

public class BasePage implements FrameworkConstants {
    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(BasePage.class);
    public WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));

        PageFactory.initElements(driver, this);

        log.info("WebDriver Initialized for: {}", this.getClass().getSimpleName());
    }
}
