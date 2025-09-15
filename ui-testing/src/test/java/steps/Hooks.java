package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import util.DriverManager;

public class Hooks {

    @Before
    public void setUp() {
        DriverManager.initDriver(); // create the driver
        DriverManager.getDriver().get("http://79.72.68.49:30124"); // open app
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver(); // close and clean up
    }
}