package com.herokuapp.theinternet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class LoginTests {

    private WebDriver driver;
    private String url = "https://the-internet.herokuapp.com/login";

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(String browser) {
        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Do not know how to start " + browser + ". Starting Chrome instead.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown(){
        //        Close browser
        driver.quit();
    }

    @Parameters({"username", "password", "flashMessage", "expextedURL"})
    @Test(priority = 1, groups = {"positiveTests", "functionalTests"})
    public void positiveLoginTest(String username, String password, String flashMessage, String expextedURL) {
//         Test steps:
//        - open test page
        driver.get(url);
//        - enter username
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys(username);
//        - enter password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
//        - click login button
        WebElement logInButton = driver.findElement(By.className("radius"));
        logInButton.click();
//        Verifications:
//        - new url address
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expextedURL, "Actual URL is different than expected.");
//        - logout button is visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(), "Log Out button is not visible.");
//        - successful login message
        WebElement loginMessage = driver.findElement(By.cssSelector("div.flash"));
        String actualMessage = loginMessage.getText();
        Assert.assertTrue(actualMessage.contains(flashMessage), "Actual message is different than we wanted.\nActual message:" + actualMessage + "\nExpected message:" + flashMessage);
    }

    @Parameters({"username", "password", "flashMessage"})
    @Test(priority = 2, groups = {"negativeTests", "functionalTests"})
    public void negativeLoginTest(String username, String password, String flashMessage) {
//         Test steps:
//        - open test page
        driver.get(url);
//        - enter username
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));
        usernameField.sendKeys(username);
//        - enter password
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
//        - click login button
        WebElement logInButton = driver.findElement(By.className("radius"));
        logInButton.click();
//        Verifications:
//        - flash message
        WebElement errorMessage = driver.findElement(By.cssSelector("div.flash"));
        String actualMessage = errorMessage.getText();
        Assert.assertTrue(actualMessage.contains(flashMessage), "Actual message is different than we wanted.\nActual message:" + actualMessage + "\nExpected message:" + flashMessage);
    }
}
