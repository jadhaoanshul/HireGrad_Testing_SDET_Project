package util;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ActionUtil extends BasePage {

    Actions actions;

    public ActionUtil(WebDriver driver) {
        super(driver);
        actions = new Actions(driver);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        element.click();
    }

    public void enterText(WebElement element, String value) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
        element.clear();
        element.sendKeys(value);
    }

    public void selectByVisibleText(WebElement element, String value) {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public boolean isDisplayed(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }

    public void mouseHover(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        actions.moveToElement(element).perform();
    }

    public void doubleClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        actions.doubleClick(element).perform();
    }

    public void rightClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        actions.contextClick(element).perform();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        wait.until(ExpectedConditions.visibilityOf(source));
        wait.until(ExpectedConditions.visibilityOf(target));
        actions.dragAndDrop(source, target).perform();
    }
}