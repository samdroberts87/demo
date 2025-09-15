package com.example.visa_movement_tracker.entities;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

public class TestPerson {

    @Nested
    @DisplayName("firstName Tests")
    class personFirstNameTests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<String> validFirstNames() {
            return Stream.of(
                    "Joe", StringUtils.repeat("a", 75),"John", "john", "joHn", " john ",
                    "billy-joel");
        }


        @ParameterizedTest
        @MethodSource("validFirstNames")
        public void testValidPersonFirstName(String firstName) {
            person.setFirstName(firstName);

            assertTrue(person.isFirstNameValid());
        }

        static Stream<String> invalidFirstNames() {
            return Stream.of(
                    null, "", " ", "-", "-john", "billy--joel", "jo", StringUtils.repeat("a", 76),
                    "John Doe", "Billy_joel", "jo3");
        }

        @ParameterizedTest
        @MethodSource("invalidFirstNames")
        public void testInvalidPersonFirstName(String firstName) {
            person.setFirstName(firstName);

            assertFalse(person.isFirstNameValid());
        }
    }

    @Nested
    @DisplayName("lastName Tests")
    class personLastNameTests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<String> validLastNames() {
            return Stream.of(
                    "Doe", StringUtils.repeat("a", 75), "doe", "doE", " doe ",
                    "billy-joel");
        }


        @ParameterizedTest
        @MethodSource("validLastNames")
        public void testValidPersonLastName(String lastName) {
            person.setLastName(lastName);

            assertTrue(person.isLastNameValid());
        }

        static Stream<String> invalidLastNames() {
            return Stream.of(
                    null, "", " ", "-", "-doe", "billy--joel", "do", StringUtils.repeat("a", 76),
                    "John Doe", "Billy_joel", "d0e");
        }

        @ParameterizedTest
        @MethodSource("invalidLastNames")
        public void testInvalidPersonLastName(String lastName) {
            person.setLastName(lastName);

            assertFalse(person.isLastNameValid());
        }
    }

    @Nested
    @DisplayName("dateOfBirth Tests")
    class personDateOfBirthTests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<LocalDate> validDateOfBirths() {
            return Stream.of(
                    LocalDate.of(2022, 11, 24),
                    LocalDate.now(),
                    LocalDate.now().plusDays(1),
                    LocalDate.of(1909, 8, 21)
            );
        }


        @ParameterizedTest
        @MethodSource("validDateOfBirths")
        public void testValidPersonDateOfBirth(LocalDate dateOfBirth) {
            person.setDateOfBirth(dateOfBirth);

            assertTrue(person.isDateOfBirthValid());
        }

        static Stream<LocalDate> invalidDateOfBirths() {
            return Stream.of(
                    null,
                    LocalDate.now().plusDays(2),
                    LocalDate.now().minusYears(125).minusDays(2)
            );
        }

        @ParameterizedTest
        @MethodSource("invalidDateOfBirths")
        public void testInvalidPersonDateOfBirth(LocalDate dateOfBirth) {
            person.setDateOfBirth(dateOfBirth);

            assertFalse(person.isDateOfBirthValid());
        }
    }

    @Nested
    @DisplayName("addressLine1 Tests")
    class personAddressLine1Tests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<String> validAddressLine1s() {
            return Stream.of(
                    "123 High Street", StringUtils.repeat("a", 75), "No. 1 Ebenezer Place");
        }


        @ParameterizedTest
        @MethodSource("validAddressLine1s")
        public void testValidPersonAddressLine1s(String addressLine1) {
            person.setAddressLine1(addressLine1);

            assertTrue(person.isAddressLine1Valid());
        }

        static Stream<String> invalidAddressLine1s() {
            return Stream.of(
                    null, "", " ",StringUtils.repeat("a", 76));
        }


        @ParameterizedTest
        @MethodSource("invalidAddressLine1s")
        public void testInvalidPersonAddressLine1s(String addressLine1) {
            person.setAddressLine1(addressLine1);

            assertFalse(person.isAddressLine1Valid());
        }


    }

    @Nested
    @DisplayName("postcode Tests")
    class personPostcodeTests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<String> validPostcodes() {
            return Stream.of(
                    "KT10 5UJ", "3762", StringUtils.repeat("A", 10),
                    StringUtils.repeat("1", 10), " SW1A 5XYZ ", "SW4A-LL90");
        }

        @ParameterizedTest
        @MethodSource("validPostcodes")
        public void testValidPostcodes(String postcodes) {
            person.setPostcode(postcodes);

            assertTrue(person.isPostcodeValid());
        }

        static Stream<String> invalidPostcodes() {
            return Stream.of(
                    null, "", " ", "KT1", "376", StringUtils.repeat("A", 11), "SW1A_5XYZ",
                    "LA&90 LH7", "KY89  UI81", "UI--901S", "-KT19 901");
        }

        @ParameterizedTest
        @MethodSource("invalidPostcodes")
        public void testInvalidPostcodes(String postcodes) {
            person.setPostcode(postcodes);

            assertFalse(person.isPostcodeValid());
        }
    }

    @Nested
    @DisplayName("email Tests")
    class personEmailTests {
        static Person person;

        @BeforeEach()
        void init() {
            person = new Person();
        }

        static Stream<String> validEmails() {
            return Stream.of(
                    "john@hotmail.com", "john.doe@gmail.com", "jo-hn@ab.cd", "jo_yu@123-a.bc.ce",
                    "john@a.ce.ac.uk", "john-3.c+1@1.a-c.c0m");
        }

        @ParameterizedTest
        @MethodSource("validEmails")
        public void testValidEmails(String email) {
            person.setEmail(email);

            assertTrue(person.isEmailValid());
        }

        static Stream<String> invalidEmails() {
            return Stream.of(
                    "", " ", "a", "a@b", "a@b..1", "a@-b.c", "a@a.-a", "a@a", "a@a.b.c.d.e");
        }

        @ParameterizedTest
        @MethodSource("invalidEmails")
        public void testInvalidEmails(String email) {
            person.setEmail(email);

            assertFalse(person.isEmailValid());
        }
    }
}
