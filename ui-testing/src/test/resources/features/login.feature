Feature: Testing Visa Holder Movement Tracker Login web page
  Scenario: Successful login with valid inputs
    Given we will launch the chrome browser
    When Login page is opened
    And we entered valid input for username "james"
    And we entered valid input for password "P1r1n01a!"
    And we clicked login button
    Then we can view the search page


  Scenario: Failed login with invalid inputs
    Given we will launch the chrome browser
    When Login page is opened
    And we entered valid input for username "wrong"
    And we entered valid input for password "pass"
    And we clicked login button
    Then we can see the pop up alert saying: "Invalid username or password"