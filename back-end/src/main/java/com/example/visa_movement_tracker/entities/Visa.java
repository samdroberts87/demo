package com.example.visa_movement_tracker.entities;

import com.example.visa_movement_tracker.entities.types.VisaTypesEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Entity
@Table(name = "Visa")
public class Visa {
    @Id
    @Column(length = 16, name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    @Column(name = "country", nullable = false)
    private String country;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "visa_type", columnDefinition = "visa_types_enum",nullable = false)
    private VisaTypesEnum visaType;
    @Column(name = "visa_start_date", nullable = false)
    private LocalDate visaStartDate;
    @Column(name = "visa_expiry_date", nullable = false)
    private LocalDate visaExpiryDate;
    @Column(name = "last_update", insertable = false, updatable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdated;

    public Visa() {
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getCountry() {
        return country;
    }

    public VisaTypesEnum getVisaType() {
        return visaType;
    }

    public LocalDate getVisaStartDate() {
        return visaStartDate;
    }

    public LocalDate getVisaExpiryDate() {
        return visaExpiryDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setVisaType(VisaTypesEnum visaType) {
        this.visaType = visaType;
    }

    public void setVisaStartDate(LocalDate visaStartDate) {
        this.visaStartDate = visaStartDate;
    }

    public void setVisaExpiryDate(LocalDate visaExpiryDate) {
        this.visaExpiryDate = visaExpiryDate;
    }

    // Proper checks for is person exists cant happen in this class without coupling this class to the person repository
    public boolean isVisaValid() {
        return Stream.of(
                isIdValid(),
                isCountryValid(),
                isVisaTypeValid(),
                isStartDateValid(),
                isExpiryDateValid()
        ).allMatch(bool -> bool == true);
    }

    public static boolean isIdValid(String id) {
        return id != null && id.length() <= 16 && id.chars().allMatch(Character::isLetterOrDigit);
    }

    public boolean isIdValid() {
        return isIdValid(this.id);
    }

    public static boolean isCountryValid(String country) {
        return country != null && country.length() > 1;
    }

    public boolean isCountryValid() {
        return isCountryValid(this.country);
    }

    // Enum already enforces that vars should be null or one of the types, so validation is just a null check
    public static boolean isVisaTypeValid(VisaTypesEnum visaType) {
        return visaType != null;
    }

    public boolean isVisaTypeValid() {
        return isVisaTypeValid(this.visaType);
    }

    // Allows dates between 125 years in the past and 1 year in the future
    public static boolean isStartDateValid(LocalDate visaStartDate) {
        return visaStartDate != null
                && visaStartDate.isBefore(LocalDate.now().plusYears(1).plusDays(1))
                && visaStartDate.isAfter(LocalDate.now().minusYears(125).minusDays(1));
    }

    public boolean isStartDateValid() {
        return isStartDateValid(this.visaExpiryDate);
    }

    // Allows dates between 125 years in the past and 7 year in the future
    public static boolean isExpiryDateValid(LocalDate visaExpiryDate) {
        return visaExpiryDate != null
                && visaExpiryDate.isBefore(LocalDate.now().plusYears(7).plusDays(1))
                && visaExpiryDate.isAfter(LocalDate.now().minusYears(125).minusDays(1));
    }

    public boolean isExpiryDateValid() {
        return isExpiryDateValid(this.visaExpiryDate);
    }
}
