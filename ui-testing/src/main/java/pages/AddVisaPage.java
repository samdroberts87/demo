package pages;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class AddVisaPage {

    private final WebDriver driver;

    private By addVisaButtonBy = By.linkText("Add Visa");
    private By firstNameBy = By.name("firstName");
    private By lastNameBy = By.name("lastName");
    private By dobBy = By.name("dateOfBirth");
    private By addressBy = By.name("addressLine1");
    private By postcodeBy = By.name("postcode");
    private By emailBy = By.name("email");
    private By saveButtonBy = By.cssSelector("button[class*='btn-addVisa']");
    private By searchButtonBy = By.cssSelector("button[type='submit']");
    private By editButtonBy = By.cssSelector("button.EditButton");
    private By removeButtonBy = By.xpath("//button[@class='btn-addVisa' and text()='Remove']");


    public AddVisaPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAddVisa() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(addVisaButtonBy)).click();
    }

    public void textbox_firstName(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(firstNameBy));
        el.clear();
        el.sendKeys(text);
    }

    public void textbox_lastName(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(lastNameBy));
        el.clear();
        el.sendKeys(text);
    }

    public void textbox_dateOfBirth(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dobBy));
        el.clear();
        el.sendKeys(text);
    }

    public void textbox_address(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(addressBy));
        el.clear();
        el.sendKeys(text);
    }

    public void textbox_postcode(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(postcodeBy));
        el.clear();
        el.sendKeys(text);
    }

    public void textbox_email(String text) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(emailBy));
        el.clear();
        el.sendKeys(text);
    }

    public void clickSave() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(saveButtonBy)).click();
    }

    public String getAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    public void clickSearch() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(searchButtonBy)).click();
    }

    public void clickEdit() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(editButtonBy)).click();
    }

    public void clickRemove() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(removeButtonBy)).click();
    }


}
