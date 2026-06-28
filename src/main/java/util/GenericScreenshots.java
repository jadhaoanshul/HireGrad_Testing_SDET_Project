package util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class GenericScreenshots implements FrameworkConstants {

    private static final Logger log = LogManager.getLogger(GenericScreenshots.class);

    /**
     * Called by TestListener automatically on every test FAILURE
     * Saves screenshot to errorShots/ folder
     * Returns full path so ExtentReport can embed the image
     */
    public static String getPhotoWithName(WebDriver driver, String testName) {

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.toString().replaceAll(":", "-");
        String fileName = testName + "_" + timestamp + ".png";
        File destFile = new File(SCREENSHOT_PATH + fileName);

        try {
            new File(SCREENSHOT_PATH).mkdirs();
            File tempFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(tempFile, destFile);
            log.info("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Screenshot failed for test: " + testName + " | " + e.getMessage());
        }

        return destFile.getAbsolutePath();
    }
}