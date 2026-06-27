package pages.admin;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AdminNotificationBellPage extends BasePage {

    private WebDriver driver;

    @FindBy(id = "admin-layout-bell-btn")
    private WebElement notificationBell;

    @FindBy(id = "admin-layout-notifications-panel")
    private WebElement notificationsPanel;

    @FindBy(xpath = "//button[starts-with(@id,'admin-layout-notification-')]")
    private List<WebElement> notificationItems;

    @FindBy(xpath = "//button[@id='admin-layout-bell-btn']//span[contains(@class,'h-2') and contains(@class,'w-2') and contains(@class,'rounded-full')]")
    private List<WebElement> unreadIndicators;

    @FindBy(xpath = "//div[@id='admin-layout-notifications-panel']//p[normalize-space()=\"You're all caught up.\"]")
    private List<WebElement> emptyNotificationMessages;

    public AdminNotificationBellPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void clickNotificationBell() {
        notificationBell.click();
    }

    public boolean isNotificationPanelDisplayed() {
        return notificationsPanel.isDisplayed();
    }

    public boolean hasNotifications() {
        return notificationItems.size() > 0;
    }

    public int getNotificationCount() {
        return notificationItems.size();
    }

    public boolean isUnreadIndicatorDisplayed() {
        return unreadIndicators.size() > 0 && unreadIndicators.get(0).isDisplayed();
    }

    public boolean isEmptyNotificationMessageDisplayed() {
        return emptyNotificationMessages.size() > 0
                && emptyNotificationMessages.get(0).isDisplayed();
    }

    public String getEmptyNotificationMessage() {
        return emptyNotificationMessages.get(0).getText();
    }

    public String getNotificationTitle(int index) {
        return notificationItems
                .get(index)
                .findElement(By.cssSelector("p.text-sm"))
                .getText();
    }

    public String getNotificationDescription(int index) {
        return notificationItems
                .get(index)
                .findElement(By.cssSelector("p.truncate"))
                .getText();
    }

    public List<String> getAllNotificationTitles() {
        List<String> titles = new ArrayList<>();

        for (WebElement notification : notificationItems) {
            titles.add(notification.findElement(By.cssSelector("p.text-sm")).getText());
        }

        return titles;
    }

    public List<String> getAllNotificationDescriptions() {
        List<String> descriptions = new ArrayList<>();

        for (WebElement notification : notificationItems) {
            descriptions.add(notification.findElement(By.cssSelector("p.truncate")).getText());
        }

        return descriptions;
    }

    public void clickNotificationByName(String notificationName) {
        driver.findElement(By.id("admin-layout-notification-" + notificationName)).click();
    }

    public boolean isNotificationDisplayed(String notificationName) {
        List<WebElement> notifications = driver.findElements(
                By.id("admin-layout-notification-" + notificationName)
        );

        return notifications.size() > 0 && notifications.get(0).isDisplayed();
    }

    public void closeNotificationPanel() {
        notificationBell.click();
    }
}