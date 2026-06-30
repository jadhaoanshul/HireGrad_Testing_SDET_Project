package com.framework.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public final class ExtentReportManager {
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            File reportFile = new File(FrameworkConstants.EXTENT_REPORT_PATH);
            File parent = reportFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            ExtentSparkReporter reporter = new ExtentSparkReporter(FrameworkConstants.EXTENT_REPORT_PATH);
            reporter.config().setDocumentTitle("HireGrad Automation Report");
            reporter.config().setReportName("HireGrad Functional Automation");
            reporter.config().setTheme(Theme.STANDARD);

            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Application", "HireGrad");
            extentReports.setSystemInfo("Framework", "Selenium TestNG Hybrid POM");
        }
        return extentReports;
    }
}
