package com.example.visa_movement_tracker.services;

import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.entities.TravelHistory;
import com.example.visa_movement_tracker.entities.request_entities.TravelHistoryRequest;
import com.example.visa_movement_tracker.entities.request_entities.adapters.TravelHistoryAdapter;
import com.example.visa_movement_tracker.entities.request_entities.adapters.VisaAdapter;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.exceptions.travel_history.InvalidTravelHistoryException;
import com.example.visa_movement_tracker.exceptions.travel_history.TravelHistoryNotFoundException;
import com.example.visa_movement_tracker.repositories.PersonRepository;
import com.example.visa_movement_tracker.repositories.TravelHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelHistoryService {
    @Autowired
    TravelHistoryRepository historyRepository;

    @Autowired
    PersonRepository personRepository;

    public TravelHistory create(TravelHistoryRequest historyRequest) {
        Person person = personRepository.findById(historyRequest.getPersonId()).orElseThrow(
                () ->  new PersonNotFoundException("No person matching Travel History's Person ID"));
        TravelHistory history = TravelHistoryAdapter.toTravelHistory(historyRequest, person);
        return historyRepository.save(history);
    }

    public List<TravelHistoryRequest> getAllWithPersonId(Long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () ->  new PersonNotFoundException("No person matching provided ID."));
        Long personId = person.getId();

        List<TravelHistory> histories = historyRepository.findByPersonId(personId);
        return histories.stream()
                .map(TravelHistoryAdapter::fromTravelHistory)
                .collect(Collectors.toList());
    }

    @Transactional
    public TravelHistoryRequest updateHistoryByDetails(TravelHistoryRequest historyRequest, Long historyId) {
        if (historyId == null)
            throw new InvalidTravelHistoryException("Travel History must have an ID for the requested action.");

        TravelHistory existingHistory = historyRepository.findById(historyId).orElseThrow(
                () -> new TravelHistoryNotFoundException("Travel History not found with ID: " + historyRequest.getId()));

        if (historyRequest.getPersonId() != null) {
            Person existingPerson = personRepository.findById(historyRequest.getPersonId()).orElseThrow(
                    () -> new PersonNotFoundException("No person matching the new Person ID"));
            existingHistory.setPerson(existingPerson);
        }
        if (historyRequest.getCountry() != null) existingHistory.setCountry(historyRequest.getCountry());
        if (historyRequest.getEntryDate() != null) existingHistory.setEntryDate(historyRequest.getEntryDate());
        if (historyRequest.getExitDate() != null) existingHistory.setExitDate(historyRequest.getExitDate());

        return TravelHistoryAdapter.fromTravelHistory(existingHistory);
    }

    public void deleteTravelHistory(Long historyId) {
        TravelHistory existingHistory = historyRepository.findById(historyId).orElseThrow(
                () -> new TravelHistoryNotFoundException("TravelHistory with given ID is not in the system."));
        historyRepository.delete(existingHistory);
    }
}
