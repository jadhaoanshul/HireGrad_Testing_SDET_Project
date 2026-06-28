package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import util.ExtentReportManager;
import util.GenericScreenshots;

import java.lang.reflect.Field;

public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Runs once when entire suite starts
    @Override
    public void onStart(ISuite suite) {
        log.info("========== TEST SUITE STARTED: "
                + suite.getName() + " ==========");
        extentReports = ExtentReportManager.getInstance();
    }

    // Runs once when entire suite finishes — writes HTML file
    @Override
    public void onFinish(ISuite suite) {
        log.info("========== TEST SUITE FINISHED: "
                + suite.getName() + " ==========");
        if (extentReports != null) {
            extentReports.flush();
            log.info("ExtentReport written successfully");
        }
    }

    // Runs before each test method
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("---------- STARTED: " + testName + " ----------");
        ExtentTest test = extentReports.createTest(
                testName,
                result.getMethod().getDescription()
        );
        extentTest.set(test);
    }

    // Runs when test method passes
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.info("PASSED: " + testName);
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    // Runs when test method fails — takes screenshot automatically
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.error("FAILED: " + testName
                + " | Reason: " + result.getThrowable().getMessage());

        WebDriver driver = getDriverFromTest(result);

        if (driver != null) {
            String screenshotPath = GenericScreenshots
                    .getPhotoWithName(driver, testName);
            try {
                extentTest.get().fail(
                        "Test Failed: " + result.getThrowable().getMessage(),
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build()
                );
            } catch (Exception e) {
                log.error("Could not attach screenshot to report: "
                        + e.getMessage());
                extentTest.get().fail(result.getThrowable());
            }
        } else {
            extentTest.get().fail(result.getThrowable());
        }
    }

    // Runs when test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        log.warn("SKIPPED: " + testName);
        extentTest.get().log(Status.SKIP,
                "Test Skipped: " + result.getThrowable());
    }

    // Gets driver field from BaseTest using reflection
    // You never need to change this — works for all test classes
    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            Object instance = result.getInstance();
            Field field = instance.getClass()
                    .getSuperclass()
                    .getDeclaredField("driver");
            field.setAccessible(true);
            return (WebDriver) field.get(instance);
        } catch (Exception e) {
            log.error("Could not get driver for screenshot: " + e.getMessage());
            return null;
        }
    }

    // Call this from any test class to add step logs to ExtentReport
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}