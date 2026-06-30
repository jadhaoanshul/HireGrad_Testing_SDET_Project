package com.tests.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.framework.util.ExtentReportManager;
import com.framework.util.GenericScreenshots;
import com.tests.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, ISuiteListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        log.info("Suite started: {}", suite.getName());
        extentReports = ExtentReportManager.getInstance();
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("Suite finished: {}", suite.getName());
        if (extentReports != null) {
            extentReports.flush();
            log.info("Extent report generated");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        EXTENT_TEST.set(test);
        log.info("Started: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        EXTENT_TEST.get().log(Status.PASS, "Test passed");
        log.info("Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();
        String message = result.getThrowable() == null ? "Test failed" : result.getThrowable().getMessage();
        log.error("Failed: {} | {}", result.getMethod().getMethodName(), message);

        // The screenshot is captured during BaseTest teardown (while the driver is
        // still alive) and handed over here. Fall back to a live capture if the
        // driver is somehow still available.
        String screenshotPath = BaseTest.consumeFailureScreenshot();
        if (screenshotPath == null && driver != null) {
            screenshotPath = GenericScreenshots.capture(driver, result.getMethod().getMethodName());
        }

        if (screenshotPath != null) {
            EXTENT_TEST.get().fail(message,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            EXTENT_TEST.get().fail(message);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message = result.getThrowable() == null ? "Skipped" : result.getThrowable().getMessage();
        EXTENT_TEST.get().log(Status.SKIP, message);
        log.warn("Skipped: {} | {}", result.getMethod().getMethodName(), message);
    }
}
