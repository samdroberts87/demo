package com.example.visa_movement_tracker.entities.request_entities;

import com.example.visa_movement_tracker.entities.types.VisaTypesEnum;

import java.time.LocalDate;

// Used for API requests so the caller doesnt have to provide the Person, only their Person's id
public class VisaRequest {
    private String id;
    private Long personId;
    private String country;
    private VisaTypesEnum visaType;
    private LocalDate visaStartDate;
    private LocalDate visaExpiryDate;

    public VisaRequest() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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

    public String getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
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
}
