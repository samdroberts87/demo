package com.example.visa_movement_tracker.entities.request_entities.adapters;


import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.entities.TravelHistory;
import com.example.visa_movement_tracker.entities.request_entities.TravelHistoryRequest;

public class TravelHistoryAdapter {
    public static TravelHistoryRequest fromTravelHistory(TravelHistory travelHistory) {
        TravelHistoryRequest request = new TravelHistoryRequest();
        request.setId(travelHistory.getId());
        request.setPersonId(travelHistory.getPerson() != null ? travelHistory.getPerson().getId() : null);
        request.setCountry(travelHistory.getCountry());
        request.setEntryDate(travelHistory.getEntryDate());
        request.setExitDate(travelHistory.getExitDate());
        request.setLastUpdated(travelHistory.getLastUpdated());
        return request;
    }

    // Only used for creating records in the database
    // Any other uses for this should use objects fetched from the Repository
    public static TravelHistory toTravelHistory(TravelHistoryRequest request, Person person) {
        TravelHistory travelHistory = new TravelHistory();
        travelHistory.setId(request.getId());
        travelHistory.setPerson(person);
        travelHistory.setCountry(request.getCountry());
        travelHistory.setEntryDate(request.getEntryDate());
        travelHistory.setExitDate(request.getExitDate());
        travelHistory.setLastUpdated(request.getLastUpdated());
        return travelHistory;
    }
}
