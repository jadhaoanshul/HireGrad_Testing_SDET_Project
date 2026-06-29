package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtentReportManager implements FrameworkConstants {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            File reportFile = new File(REPORT_PATH);
            File reportDirectory = reportFile.getParentFile();
            if (reportDirectory != null && !reportDirectory.exists()) {
                reportDirectory.mkdirs();
            }

            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("HireGrad Test Report");
            spark.config().setReportName("Automation Results - HireGrad");

            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("Application", "HireGrad");
            extentReports.setSystemInfo("Tester", "SDET Team");

            log.info("ExtentReports instance created");
        }
        return extentReports;
    }
}
