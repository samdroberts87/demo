package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import util.DriverManager;

public class LoginSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    @Given("we will launch the chrome browser")
    public void we_will_launch_the_chrome_browser() {
    }
    @When("Login page is opened")
    //connecting to live host

    public void login_page_is_opened() {
//        driver.get("http://79.72.68.49:30124");
    }  // to be changed for production

    @When("we entered valid input for username {string}")
    public void we_entered_valid_input_for_username(String username) {
        loginPage.textbox_userName(username);
    }

    @When("we entered valid input for password {string}")
    public void we_entered_valid_input_for_password(String password) {
        loginPage.textbox_password(password);
    }

    @When("we clicked login button")
    public void we_clicked_login_button() {
        loginPage.button_login();
    }

    @Then("we can view the search page")
    public void we_can_view_the_search_page() {
        boolean isSearchPageVisible = driver.getCurrentUrl().contains("search");
        assert isSearchPageVisible : "Search page not visible. Actual url: " + driver.getCurrentUrl();
     }

    @Then("we can see the pop up alert saying: {string}")
    public void we_can_see_the_pop_out_alert_saying_invalid_username_or_password(String expectedAlert) {
            String alertText = loginPage.getAlertMessage();
            assert alertText.equals(expectedAlert) : "Unexpected alert text: " + alertText;
    }
}
