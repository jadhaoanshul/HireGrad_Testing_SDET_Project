package com.framework.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public final class GenericScreenshots {
    private GenericScreenshots() {
    }

    public static String capture(WebDriver driver, String testName) {
        File directory = new File(FrameworkConstants.SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = testName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(directory, fileName);
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to save screenshot", exception);
        }
        // Return a path relative to the Extent report location so the captured image
        // renders correctly inside the generated HTML report (an absolute Windows
        // path does not load as an <img> src in the report).
        return reportRelativePath(destination);
    }

    private static String reportRelativePath(File screenshotFile) {
        File reportDirectory = new File(FrameworkConstants.EXTENT_REPORT_PATH).getParentFile();
        try {
            return reportDirectory.toPath().toAbsolutePath()
                    .relativize(screenshotFile.toPath().toAbsolutePath())
                    .toString()
                    .replace(File.separatorChar, '/');
        } catch (RuntimeException exception) {
            return screenshotFile.getAbsolutePath();
        }
    }
}
