package com.example.visa_movement_tracker.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Travel_History")
public class TravelHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "entry_date", nullable = false)
    private LocalDate visaEntryDate;
    @Column(name = "exit_date")
    private LocalDate visaExitDate;
    @Column(name = "last_update", insertable = false, updatable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdated;

    public TravelHistory() {
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getEntryDate() {
        return visaEntryDate;
    }

    public LocalDate getExitDate() {
        return visaExitDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEntryDate(LocalDate visaEntryDate) {
        this.visaEntryDate = visaEntryDate;
    }

    public void setExitDate(LocalDate visaExitDate) {
        this.visaExitDate = visaExitDate;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
