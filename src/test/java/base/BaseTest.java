package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import util.PropertyFile;

import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {

        log.info("Setting up browser: {}", browser);

        if(browser.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
            log.info("Chrome Browser Launched");
        }
        else if(browser.equalsIgnoreCase("edge")){
            driver = new EdgeDriver();
            log.info("Edge Browser Launched");
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String url = new PropertyFile().getValueProperty("url");
        driver.get(url);
        log.info("Navigated to URL: {}", url);

    }

    @AfterMethod(alwaysRun = true)
    public void logoutOfApplication() {
        driver.navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        if(driver != null){
            driver.quit();
            log.info("Browser closed successfully");
        }
    }
}
