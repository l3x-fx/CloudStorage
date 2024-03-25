package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {
    private WebDriver driver;
    private WebDriverWait wait;

    public Helper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 2);
    }
        public void clickById(String id) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            WebElement button = driver.findElement(By.id(id));
            button.click();
        }
        public void clickByPath(String element, String idFragment) {
            String path = String.format("//%s[starts-with(@id, '%s')]", element, idFragment);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
            WebElement button = driver.findElement(By.xpath(path));
            button.click();
        }
        public void enterTextById(String id, String text) {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            WebElement element = driver.findElement(By.id(id));
            element.click();
            element.sendKeys(text);
        }
}
