package com.framework.util;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ActionUtil {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavaScriptUtil javaScriptUtil;

    public ActionUtil(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        this.javaScriptUtil = new JavaScriptUtil(driver);
    }

    public void click(WebElement element) {
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            clickable.click();
        } catch (ElementClickInterceptedException exception) {
            javaScriptUtil.scrollIntoView(clickable);
            javaScriptUtil.click(clickable);
        }
    }

    public void click(By locator) {
        click(wait.until(ExpectedConditions.elementToBeClickable(locator)));
    }

    public void enterText(WebElement element, String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(element));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(value == null ? "" : value);
    }

    public void enterText(By locator, String value) {
        enterText(wait.until(ExpectedConditions.elementToBeClickable(locator)), value);
    }

    public void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void selectByVisibleText(WebElement element, String text) {
        new Select(wait.until(ExpectedConditions.visibilityOf(element))).selectByVisibleText(text);
    }

    public void selectFirstRealOption(By locator) {
        Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
        for (WebElement option : select.getOptions()) {
            String value = option.getAttribute("value");
            if (value != null && !value.trim().isEmpty()) {
                select.selectByVisibleText(option.getText().trim());
                return;
            }
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public String text(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
        } catch (NoSuchElementException exception) {
            return "";
        }
    }

    public void waitForUrlContains(String route) {
        wait.until(ExpectedConditions.urlContains(route));
    }

    public void waitForAny(By... locators) {
        wait.until(driver -> {
            for (By locator : locators) {
                if (!driver.findElements(locator).isEmpty()) {
                    return true;
                }
            }
            return false;
        });
    }

    public int count(By locator) {
        return driver.findElements(locator).size();
    }

    public List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }
}
