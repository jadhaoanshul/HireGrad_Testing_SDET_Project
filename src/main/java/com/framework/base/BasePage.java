package com.framework.base;

import com.framework.util.ActionUtil;
import com.framework.util.JavaScriptUtil;
import com.framework.util.PropertyFile;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BasePage {
    protected final WebDriver driver;
    protected final ActionUtil action;
    protected final JavaScriptUtil js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeout = Integer.parseInt(new PropertyFile().getValueProperty("explicitWaitSeconds", "20"));
        this.action = new ActionUtil(driver, timeout);
        this.js = new JavaScriptUtil(driver);
        PageFactory.initElements(driver, this);
    }

    protected boolean displayed(By locator) {
        return action.isDisplayed(locator);
    }

    protected boolean present(By locator) {
        return action.isPresent(locator);
    }

    protected int count(By locator) {
        return action.count(locator);
    }

    protected List<WebElement> all(By locator) {
        return action.findAll(locator);
    }

    protected boolean everyRowContains(By rowLocator, String expectedText) {
        List<WebElement> rows = all(rowLocator);
        if (rows.isEmpty()) {
            return false;
        }
        String needle = expectedText == null ? "" : expectedText.toLowerCase();
        for (WebElement row : rows) {
            if (!row.getText().toLowerCase().contains(needle)) {
                return false;
            }
        }
        return true;
    }
}
