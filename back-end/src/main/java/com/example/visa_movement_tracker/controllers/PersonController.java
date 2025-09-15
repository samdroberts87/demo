package com.example.visa_movement_tracker.controllers;

import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.exceptions.person.InvalidPersonAttributeException;
import com.example.visa_movement_tracker.exceptions.person.InvalidPersonException;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.services.PersonService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PersonController {
    @Autowired
    PersonService personService;

    @Autowired
    EntityManager entityManager;

    @GetMapping("/person/get/all")
    public ResponseEntity<List<Person>> getPersons() {
        return ResponseEntity.ok(personService.getAll());
    }

    // In person for JPA to retain info on the inserted person and refresh it,
    // save() and refresh() must be done in the same transaction
    @Transactional
    @PostMapping("/person/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        try {
            Person insertedPerson = personService.create(person);
//         insertedPerson's last_update is null, but is set as current time by repository after insertion
            entityManager.refresh(insertedPerson);
            return ResponseEntity.ok(insertedPerson);
        } catch (InvalidPersonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Needs at minimum ID to work
    @Transactional
    @PutMapping("/person/update/details")
    public ResponseEntity<Person> updatePersonsByDetails(@RequestBody Person person) {

        try {
            Person updatedPerson = personService.updatePersonByDetails(person);
//            entityManager.refresh(updatedPerson);
            return ResponseEntity.ok(updatedPerson);
        } catch (InvalidPersonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/person/search/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable("id") Long id) {
        try {
            Optional<Person> person = personService.findPersonByUnvalidatedId(id);
            if (person.isPresent()) return ResponseEntity.ok(person.get());
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidPersonAttributeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Searches for all people that have the same details as the details provided
    // The JSON could be a simple single key:value of email:value to searching for every attribute of Person
    @GetMapping("/person/search/details")
    public ResponseEntity<List<Person>> findPersonsByDetails(@RequestBody Person person) {
        try {
            List<Person> people = personService.findPersonByDetails(person);
            if (people.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            else return ResponseEntity.ok(people);
        } catch (InvalidPersonAttributeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping("/person/delete")
    public ResponseEntity<String> deletePerson(@RequestParam Long id) {
        if (personService.deletePerson(id))
            return ResponseEntity.ok("Successfully deleted Person with ID: " + id);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
