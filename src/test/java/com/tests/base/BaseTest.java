package com.tests.base;

import com.framework.pages.AdminLayoutPage;
import com.framework.pages.LoginPage;
import com.framework.pages.StudentLayoutPage;
import com.framework.util.ExcelUtil;
import com.framework.util.FrameworkConstants;
import com.framework.util.GenericScreenshots;
import com.framework.util.PropertyFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected PropertyFile propertyFile;
    protected ExcelUtil excelUtil;
    protected final Logger log = LogManager.getLogger(getClass());
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<String> FAILURE_SCREENSHOT = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("") String browser) {
        propertyFile = new PropertyFile();
        excelUtil = new ExcelUtil(FrameworkConstants.TEST_DATA_PATH);
        String browserName = browser == null || browser.isBlank()
                ? propertyFile.getValueProperty("browser", "chrome")
                : browser;

        log.info("Launching browser: {}", browserName);
        if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless",
                    propertyFile.getValueProperty("headless", "false")))) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1440,1000");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
        }

        DRIVER.set(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(propertyFile.getValueProperty("implicitWaitSeconds", "4"))));
        String appUrl = System.getProperty("url", propertyFile.getValueProperty("url"));
        driver.get(appUrl);
        log.info("Application opened: {}", appUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Capture the failure screenshot while the browser is still alive. The
        // TestNG listener's onTestFailure can run after this teardown, by which
        // time the driver would already be quit, so the screenshot must be taken
        // here and handed to the listener through a ThreadLocal.
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            try {
                String path = GenericScreenshots.capture(driver, result.getMethod().getMethodName());
                FAILURE_SCREENSHOT.set(path);
                log.info("Failure screenshot captured: {}", path);
            } catch (RuntimeException exception) {
                log.error("Unable to capture failure screenshot", exception);
            }
        }
        if (driver != null) {
            driver.quit();
            log.info("Browser closed");
        }
        DRIVER.remove();
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static String consumeFailureScreenshot() {
        String path = FAILURE_SCREENSHOT.get();
        FAILURE_SCREENSHOT.remove();
        return path;
    }

    protected Map<String, String> data(String testCaseId) {
        return excelUtil.getRowData("TestData", testCaseId);
    }

    protected void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin(
                propertyFile.getValueProperty("adminUsername"),
                propertyFile.getValueProperty("adminPassword"));
        waitForRoute("/admin/home");
        log.info("Admin login successful");
    }

    protected void loginAsStudent() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsStudent(
                propertyFile.getValueProperty("studentUsername"),
                propertyFile.getValueProperty("studentPassword"));
        waitForRoute("/student/home");
        log.info("Student login successful");
    }

    protected void waitForRoute(String route) {
        new WebDriverWait(driver, Duration.ofSeconds(
                Integer.parseInt(propertyFile.getValueProperty("explicitWaitSeconds", "20"))))
                .until(ExpectedConditions.urlContains(route));
    }

    protected void sleepBriefly() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    protected void skipWhenNoData(boolean noDataCondition, String message) {
        if (noDataCondition) {
            throw new SkipException(message);
        }
    }

    protected AdminLayoutPage adminLayout() {
        return new AdminLayoutPage(driver);
    }

    protected StudentLayoutPage studentLayout() {
        return new StudentLayoutPage(driver);
    }
}
