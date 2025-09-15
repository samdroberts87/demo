package com.example.visa_movement_tracker.entities.request_entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TravelHistoryRequest {
    private Long id;
    private Long personId;
    private String country;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private LocalDateTime lastUpdated;

    public Long getId() {
        return id;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEntryDate(LocalDate visaEntryDate) {
        this.entryDate = visaEntryDate;
    }

    public void setExitDate(LocalDate visaExitDate) {
        this.exitDate = visaExitDate;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
