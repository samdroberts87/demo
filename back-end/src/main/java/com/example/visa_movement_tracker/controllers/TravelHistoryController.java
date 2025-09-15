package com.example.visa_movement_tracker.controllers;

import com.example.visa_movement_tracker.entities.TravelHistory;
import com.example.visa_movement_tracker.entities.request_entities.TravelHistoryRequest;
import com.example.visa_movement_tracker.entities.request_entities.adapters.TravelHistoryAdapter;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.exceptions.travel_history.IllegalTravelHistoryInsertionException;
import com.example.visa_movement_tracker.exceptions.travel_history.InvalidTravelHistoryException;
import com.example.visa_movement_tracker.exceptions.travel_history.TravelHistoryNotFoundException;
import com.example.visa_movement_tracker.services.TravelHistoryService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class TravelHistoryController {
    @Autowired
    TravelHistoryService historyService;

    @Autowired
    EntityManager entityManager;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<TravelHistoryRequest> createTravelHistory(@RequestBody TravelHistoryRequest historyRequest) {
        TravelHistory insertedHistory = historyService.create(historyRequest);
        entityManager.refresh(insertedHistory);
        TravelHistoryRequest insertedHistoryRequest = TravelHistoryAdapter.fromTravelHistory(insertedHistory);
        return ResponseEntity.ok(insertedHistoryRequest);
    }

    // Needs at minimum ID to work
    @PutMapping("/update/details")
    public ResponseEntity<TravelHistoryRequest> updateTravelHistoryByDetails(@RequestBody TravelHistoryRequest historyRequest,
                                                                             @RequestParam Long id) {
        try {
            TravelHistoryRequest updatedHistoryRequest = historyService.updateHistoryByDetails(historyRequest, id);
            return ResponseEntity.ok(updatedHistoryRequest);
        } catch (InvalidTravelHistoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (TravelHistoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalTravelHistoryInsertionException | PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeTravelHistory(@RequestParam Long id) {
        try {
            historyService.deleteTravelHistory(id);
        } catch (TravelHistoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Successfully deleted Travel History with ID: " + id);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<List<TravelHistoryRequest>> findPersonById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(historyService.getAllWithPersonId(id));
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
