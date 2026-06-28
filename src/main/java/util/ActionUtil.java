package util;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;

public class ActionUtil {

    Actions actions;

    public ActionUtil(WebDriver driver) {
        actions = new Actions(driver);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void enterText(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public void mouseHover(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    public void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }
}