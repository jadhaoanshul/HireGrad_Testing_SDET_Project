package util;

public interface FrameworkConstants {

    String PROPERTYFILE_PATH = System.getProperty("user.dir") +
            "/src/test/resources/ConfigProperty/config.properties";

    String EXCEL_PATH = System.getProperty("user.dir") +
            "/src/test/resources/TestData/TestData.xlsx";

    String JOB_LISTING_DATA_PATH = System.getProperty("user.dir") +
            "/src/test/resources/TestData/JobListingData.xlsx";

    String SCREENSHOT_PATH = "./Screenshots/";

    String REPORT_PATH = "./test-output/ExtentReports/HireGradReport.html";

    int IMPLICIT_TIMEOUT = 10;
    int EXPLICIT_TIMEOUT = 15;

}
