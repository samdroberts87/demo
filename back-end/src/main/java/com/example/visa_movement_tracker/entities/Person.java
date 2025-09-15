package com.example.visa_movement_tracker.entities;

import jakarta.persistence.*;
import org.apache.commons.text.WordUtils;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "address_line_1", nullable = false)
    private String addressLine1;
    @Column(name = "postcode", nullable = false)
    private String postcode;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "last_update", insertable = false, updatable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdated;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setFirstName(String firstName) {

        this.firstName = (firstName == null) ? null : WordUtils.capitalizeFully(firstName.trim(),' ', '-');
    }

    public void setLastName(String lastName) {
        this.lastName = (lastName == null) ? null : WordUtils.capitalizeFully(lastName.trim(), ' ', '-');
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = (addressLine1 == null) ? null : WordUtils.capitalizeFully(addressLine1.trim(), '-');
    }

    public void setPostcode(String postcode) {
        this.postcode = (postcode == null) ? null : postcode.trim().toUpperCase();
    }

    public void setEmail(String email) {
        this.email = (email == null) ? null : email.trim();
    }

    public boolean isPersonValid() {
        return Stream.of(
                isFirstNameValid(),
                isLastNameValid(),
                isDateOfBirthValid(),
                isAddressLine1Valid(),
                isEmailValid()
        ).allMatch(bool -> bool == true);
    }

    /*
    * At minimum, all validation methods will mirror the constraints of the database columns
    * */

    // Database uses BIGSERIAL, which counts from 1
    public static boolean isIdValid(Long id) {
        return id != null && id >= 1;
    }

    public boolean isIdValid() {
        return Person.isIdValid(this.id);
    }

    // Both first name and last name allow for double-barrelled names
    public static boolean isFirstNameValid(String firstName) {
        String firstNamePattern = "^[A-Za-z]+(-[A-Z][a-z]+)?$";
        return firstName != null && Pattern.matches(firstNamePattern, firstName) &&
                firstName.length() >= 3 && firstName.length() <= 75;
    }
    public boolean isFirstNameValid() {
        return Person.isFirstNameValid(this.firstName);
    }

    public static boolean isLastNameValid(String lastName) {
        String lastNamePattern = "^[A-Za-z]+(-[A-Z][a-z]+)?$";
        return lastName != null && Pattern.matches(lastNamePattern, lastName) &&
                lastName.length() >= 3 && lastName.length() <= 75;
    }
    public boolean isLastNameValid() {
        return Person.isLastNameValid(this.lastName);
    }

    // Ambiguous time zone of application means it should accept date of births that are newborns in an ahead time zone
    // This does not account for the accepted but possibly erroneous date between
    // the furthest time zone (also considering DST) and the application's date plus one
    // and where the hour difference between the application's time zone and user's time zone is greater than 24hrs
    public static boolean isDateOfBirthValid(LocalDate dateOfBirth) {
        return dateOfBirth != null && dateOfBirth.isBefore(LocalDate.now().plusDays(2))
                && dateOfBirth.isAfter(LocalDate.now().minusYears(125).minusDays(1));
    }

    public boolean isDateOfBirthValid() {
        return Person.isDateOfBirthValid(this.dateOfBirth);
    }

    // The complexity of this validation is beyond the scope of the project's time/abilities, unless APIs are used
    public static boolean isAddressLine1Valid(String addressLine1) {
        return addressLine1 != null && !addressLine1.isEmpty() && addressLine1.length() <= 75;
    }

    public boolean isAddressLine1Valid() {
        return Person.isAddressLine1Valid(this.addressLine1);
    }

    // Creating a global postal code regex is beyond the scope of the project
    public static boolean isPostcodeValid(String postcode) {
        String postcodePattern = "^[a-zA-Z0-9]+(( [a-zA-Z0-9]+)?(-[a-zA-Z0-9]+)?)*$";
        return postcode != null && postcode.length() >= 4 && postcode.length() <= 10
                && Pattern.matches(postcodePattern, postcode);
    }

    public boolean isPostcodeValid() {
        return Person.isPostcodeValid(this.postcode);
    }

    public static boolean isEmailValid(String email) {
        String emailUsername = "([a-zA-Z0-9]+)([.,_+-]?[a-zA-Z0-9]+)*";
        String domainSection = "[a-zA-Z0-9]+(-[a-zA-Z0-9]+)*";
        String emailPattern = "^" + emailUsername + "@" + domainSection + "(." + domainSection + "){1,3}" + "$";
        return email != null && Pattern.matches(emailPattern, email) && email.length() < 256;
    }

    public boolean isEmailValid() {
        return Person.isEmailValid(this.email);
    }
}
