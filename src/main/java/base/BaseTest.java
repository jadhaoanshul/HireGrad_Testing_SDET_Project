package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import util.PropertyFile;

import java.time.Duration;

public class BaseTest {
    WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("edge") String browser) {
        if(browser.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("edge")){
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String url = new PropertyFile().getValueProperty("url");
        driver.get(url);

    }
}
