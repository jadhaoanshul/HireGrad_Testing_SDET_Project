package util;

public interface FrameworkConstants {

    String PROPERTYFILE_PATH = System.getProperty("user.dir") +
            "/src/test/resources/ConfigProperty/config.properties";

    String EXCEL_PATH = System.getProperty("user.dir") +
            "/src/test/resources/Test Data/TestData.xlsx";

    String SCREENSHOT_PATH = "./Screenshots/";

    String REPORT_PATH = "./test-output/ExtentReports/HireGradReport.html";

    int IMPLICIT_TIMEOUT = 10;
    int EXPLICIT_TIMEOUT = 15;

}
