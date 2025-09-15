package com.example.visa_movement_tracker.controllers;

import com.example.visa_movement_tracker.entities.Visa;
import com.example.visa_movement_tracker.entities.request_entities.VisaRequest;
import com.example.visa_movement_tracker.entities.request_entities.adapters.VisaAdapter;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.exceptions.visa.IllegalVisaInsertion;
import com.example.visa_movement_tracker.exceptions.visa.InvalidVisaAttributeException;
import com.example.visa_movement_tracker.exceptions.visa.VisaNotFoundException;
import com.example.visa_movement_tracker.services.PersonService;
import com.example.visa_movement_tracker.services.VisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visa")
public class VisaController {
    @Autowired
    VisaService visaService;
    
    @PostMapping("/create")
    public ResponseEntity<VisaRequest> createPerson(@RequestBody VisaRequest visaRequest) {
        try {
            Visa insertedVisa = visaService.create(visaRequest);
            VisaRequest insertedVisaRequest = VisaAdapter.fromVisa(insertedVisa);
            return ResponseEntity.ok(insertedVisaRequest);
        } catch (PersonNotFoundException | InvalidVisaAttributeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<List<VisaRequest>> findPersonById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(visaService.getAllWithPersonId(id));
        } catch (PersonNotFoundException | InvalidVisaAttributeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Needs at minimum ID to work
    @PutMapping("/update/details")
    public ResponseEntity<VisaRequest> updateVisasByDetails(@RequestBody VisaRequest visaRequest,
                                                            @RequestParam String id) {
        try {
            VisaRequest updatedVisa = visaService.updateVisaByDetails(visaRequest, id);
            return ResponseEntity.ok(updatedVisa);
        } catch (InvalidVisaAttributeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (VisaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalVisaInsertion | PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeVisa(@RequestParam String id) {
        try {
            visaService.deleteVisa(id);
        } catch (VisaNotFoundException | InvalidVisaAttributeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Successfully deleted Visa with ID: " + id);
    }
}
