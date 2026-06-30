package com.framework.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ThemePage extends BasePage {
    public ThemePage(WebDriver driver) {
        super(driver);
    }

    public void toggleTheme() {
        action.click(By.id("theme-toggle-btn"));
    }

    public String currentHtmlClass() {
        return driver.findElement(By.tagName("html")).getAttribute("class");
    }
}
