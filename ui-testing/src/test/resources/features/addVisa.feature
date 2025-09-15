Feature: Testing adding/editing/deleting visa holder

  Background:
    Given We are logged in as "james" with password "P1r1n01a!"

    Scenario: Successful addition when provided with correct data
      When we click AddVisa
      And we enter the first name "Harry"
      And we enter the last name "Potter"
      And we enter date of birth "06061966"
      And we enter address "Hogwarts"
      And we enter postcode "HGW"
      And we enter email "harry.potter@hgw.com"
      And we click Save
      Then we will see the pop up alert saying: "Saved"


  Scenario: Unsuccessful addition  when provided with not enough details
      When we click AddVisa
      And we enter the first name "Harry"
      And we enter the last name "Potter"
      And we enter date of birth "06061966"
      And we enter address "Hogwarts"
      And we enter email "harry.potter@hgw.com"
      And we click Save
      Then we will see the pop up alert saying: "Please fix the highlighted fields."


  Scenario: Successful edit when provided with correct data
      When we enter the first name "Harry"
      And we enter the last name "Potter"
      And we enter date of birth "06061966"
      And we enter address "Hogwarts"
      And we enter postcode "HGW"
      And we enter email "harry.potter@hgw.com"
      And we click Search button
      And we click Edit button
      And we change postcode to "HG"
      And we click Update
      Then we will see the pop up alert saying: "Updated successfully!"

    Scenario: Unsuccessful edit when provided with wrong data
      When we enter the first name "Harry"
      And we enter the last name "Potter"
      And we enter date of birth "06061966"
      And we enter address "Hogwarts"
      And we enter postcode "HG"
      And we enter email "harry.potter@hgw.com"
      And we click Search button
      And we click Edit button
      And we change email to "harry.potter"
      And we click Update
      Then we will see the pop up alert saying: "Please fix the highlighted fields."

  Scenario: Successful deletion of Visa Holder
    When we enter the first name "Harry"
    And we enter the last name "Potter"
    And we enter date of birth "06061966"
    And we enter address "Hogwarts"
    And we enter postcode "HG"
    And we enter email "harry.potter@hgw.com"
    And we click Search button
    And we click Edit button
    And we click Remove
    Then we will see the pop up alert saying: "Are you sure you want to remove this visa holder? This cannot be undone."
    Then we will see the pop up alert saying: "Visa holder removed."
