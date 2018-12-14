package system.web;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class AdminSeleniumTest {

    private static WebDriver browser;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver");
        browser = new ChromeDriver();
    }

    @Test
    public void testChromeSelenium() {
        browser.get("http://localhost:8080/railway-site/");
        new WebDriverWait(browser, 50).until(ExpectedConditions.visibilityOfElementLocated(By.id("loginBtn")));
        browser.findElement(By.id("loginBtn")).click();
        browser.findElement(By.id("username")).sendKeys("admin");
        browser.findElement(By.id("password")).sendKeys("adminadmin");

        browser.findElement(By.id("loginButton")).click();
    }

    @After
    public void tearDown(){
        if (browser != null) {
            browser.close();
            browser.quit();
        }
    }

}
