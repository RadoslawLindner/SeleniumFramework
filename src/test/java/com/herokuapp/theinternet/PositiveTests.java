package com.herokuapp.theinternet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test
    public void loginTest() {
//         Test steps:
//        - create driver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
//        - maximize driver window
        driver.manage().window().maximize();
//        - open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
//        - enter username
        WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys("tomsmith");
//        - enter password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");
//        - click login button
        WebElement logInButton = driver.findElement(By.className("radius"));
        logInButton.click();
//        Verifications:
//        - new url address
        String expextedURL = "https://the-internet.herokuapp.com/secure";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL,expextedURL, "Actual URL is different than expected.");
//        - logout button is visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(), "Log Out button is not visible.");
//        - successful login message
        WebElement loginMessage = driver.findElement(By.cssSelector("div.flash.success"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = loginMessage.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage),"Actual message is different than expected.");
//        Close browser
        driver.quit();
    }
}
