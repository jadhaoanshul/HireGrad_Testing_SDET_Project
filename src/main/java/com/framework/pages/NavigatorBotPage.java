package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigatorBotPage extends BasePage {
    @FindBy(id = "bot-launcher-btn")
    private WebElement launcherButton;
    @FindBy(id = "bot-input")
    private WebElement botInput;
    @FindBy(id = "bot-send-btn")
    private WebElement sendButton;
    @FindBy(id = "bot-close-btn")
    private WebElement closeButton;

    public NavigatorBotPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        action.click(launcherButton);
    }

    public void close() {
        action.click(closeButton);
    }

    public boolean isOpen() {
        return displayed(By.id("bot-chat-panel"));
    }

    public void sendMessage(String message) {
        action.enterText(botInput, message);
        action.click(sendButton);
    }

    public boolean hasShortcutChips() {
        return count(By.cssSelector("[id^='bot-chip-']")) > 0;
    }
}
