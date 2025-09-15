package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import pages.LoginPage;
import pages.AddVisaPage;
import util.DriverManager;


public class AddVisaSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    private final AddVisaPage addVisaPage = new AddVisaPage(driver);

    @Given("We are logged in as {string} with password {string}")
    public void we_are_logged_in_as_with_password(String username, String password) {
        loginPage.textbox_userName(username);
        loginPage.textbox_password(password);
        loginPage.button_login();
    }

    @When("we click AddVisa")
    public void weClickAddVisa() {
        addVisaPage.clickAddVisa();
    }

    @When("we enter the first name {string}")
    public void weEnterTheFirstName(String firstName) {
        System.out.println("first name: " + firstName);
        addVisaPage.textbox_firstName(firstName);
    }

    @When("we enter the last name {string}")
    public void weEnterTheLastName(String lastName) {
        addVisaPage.textbox_lastName(lastName);
    }

    @When("we enter date of birth {string}")
    public void weEnterDateOfBirth(String dob) {
        addVisaPage.textbox_dateOfBirth(dob);
    }

    @When("we enter address {string}")
    public void weEnterAddress(String address) {
        addVisaPage.textbox_address(address);
    }

    @When("we enter postcode {string}")
    public void weEnterPostcode(String postcode) {
        addVisaPage.textbox_postcode(postcode);
    }

    @When("we enter email {string}")
    public void weEnterEmail(String email) {
        System.out.println("email: " + email);
        addVisaPage.textbox_email(email);
    }

    @When("we click Save")
    public void weClickSave() {
        System.out.println("we clicking");
        addVisaPage.clickSave();
    }

    @Then("we will see the pop up alert saying: {string}")
    public void weWillSeeThePopUpAlertSaying(String expectedAlert) {
        String alertText = addVisaPage.getAlert();
        assert alertText.equals(expectedAlert) : "Unexpected alert text: " + alertText;
    }

    @When("we click Search button")
    public void we_click_search_button() {
        addVisaPage.clickSearch();
    }

    @When("we change postcode to {string}")
    public void we_change_postcode_to(String postcode) {
        addVisaPage.textbox_postcode(postcode);
    }

    @When("we click Update")
    public void weClickUpdate() {
        System.out.println("we clicking update");
        addVisaPage.clickSave();
    }

    @When("we click Edit button")
    public void we_click_edit_button() {
        System.out.println("we clicking edit");
        addVisaPage.clickEdit();
    }

    @When("we change email to {string}")
    public void we_change_email_to(String email) {
        addVisaPage.textbox_email(email);

    }

    @When("we click Remove")
    public void we_click_remove() {
        addVisaPage.clickRemove();
    }

}
