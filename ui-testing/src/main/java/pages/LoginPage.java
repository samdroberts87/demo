package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    WebDriver driver = null;  // object of WebDriver will be passed from test class
    WebElement username = null;
    WebElement password = null;
    WebElement btnLogin = null;

    // Injecting of driver object at constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    // Methods will be written based on elements on the page
    public void textbox_userName(String text) {
        username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys(text);
    }

    public void textbox_password(String text) {
        password =  driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys(text);
    }

    public void button_login() {
        btnLogin = driver.findElement(By.cssSelector("button[type='submit']"));
        btnLogin.click();
    }
    public String getAlertMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

}