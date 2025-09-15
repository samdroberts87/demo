package com.example.visa_movement_tracker.services;

import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.entities.Visa;
import com.example.visa_movement_tracker.entities.request_entities.VisaRequest;
import com.example.visa_movement_tracker.entities.request_entities.adapters.VisaAdapter;
import com.example.visa_movement_tracker.exceptions.visa.IllegalVisaInsertion;
import com.example.visa_movement_tracker.exceptions.visa.InvalidVisaAttributeException;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.exceptions.visa.VisaNotFoundException;
import com.example.visa_movement_tracker.repositories.PersonRepository;
import com.example.visa_movement_tracker.repositories.VisaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisaService {
    @Autowired
    VisaRepository visaRepository;

    @Autowired
    PersonRepository personRepository;

    public Person findPersonOrThrowException(Long id) {
        return personRepository.findById(id).orElseThrow(
                () ->  new PersonNotFoundException("Person not found with ID: " + id));
    }

    public Visa findVisaOrThrowException(String id) {
        if (!Visa.isIdValid(id))
            throw new InvalidVisaAttributeException("Visa must have an ID for the requested action.");
        return visaRepository.findById(id).orElseThrow(
                () ->  new VisaNotFoundException("Visa not found with ID: " + id));
    }

    public Visa create(VisaRequest visaRequest) {
        Person person = findPersonOrThrowException(visaRequest.getPersonId());
        Visa visa = VisaAdapter.toVisa(visaRequest, person);
        if (!visa.isVisaValid()) throw new InvalidVisaAttributeException("One or more Visa Attributes are not valid.");
        else return visaRepository.save(visa);
    }

    public List<VisaRequest> getAllWithPersonId(Long id) {
        Person person = findPersonOrThrowException(id);
        Long personId = person.getId();

        List<Visa> visas = visaRepository.findByPersonId(personId);
        return visas.stream()
                .map(VisaAdapter::fromVisa)
                .collect(Collectors.toList());
    }

    // If visa creationDate (or other archiving columns) is ever added as a column OR a column references Visa.id,
    //  this method will have the side effect of deleting/replacing that
    // This is due to not being able to alter the primary key of a database entry with repositories
    // If an extra column like the aforementioned column is added, deprecate this function and
    // replace with a native SQL query to possibly solve the issue
    @Transactional
    public VisaRequest updateVisaByDetails(VisaRequest visaRequest, String visaId) {
        Visa existingVisa = findVisaOrThrowException(visaId);

        if (visaRequest.getPersonId() != null) {
            Person existingPerson = findPersonOrThrowException(visaRequest.getPersonId());
            existingVisa.setPerson(existingPerson);
        }
        if (visaRequest.getCountry() != null) existingVisa.setCountry(visaRequest.getCountry());
        if (visaRequest.getVisaType() != null) existingVisa.setVisaType(visaRequest.getVisaType());
        if (visaRequest.getVisaStartDate() != null) existingVisa.setVisaStartDate(visaRequest.getVisaStartDate());
        if (visaRequest.getVisaExpiryDate() != null) existingVisa.setVisaExpiryDate(visaRequest.getVisaExpiryDate());

        if (!existingVisa.isVisaValid())
            throw new InvalidVisaAttributeException("One or more Visa Attributes are not valid.");

        // Could be optimised by putting this first in the null checks and doing the other null checks with the new
        // visa instead of the existing one.
        if (visaRequest.getId() != null && Visa.isIdValid(visaRequest.getId())) {
            if (visaRepository.existsById(visaRequest.getId()))
                throw new IllegalVisaInsertion("Visa with given ID is already in the system.");

            Visa duplicatedVisa = new Visa();
            duplicatedVisa.setId(visaRequest.getId());
            duplicatedVisa.setPerson(existingVisa.getPerson());
            duplicatedVisa.setCountry(existingVisa.getCountry());
            duplicatedVisa.setVisaType(existingVisa.getVisaType());
            duplicatedVisa.setVisaStartDate(existingVisa.getVisaStartDate());
            duplicatedVisa.setVisaExpiryDate(existingVisa.getVisaExpiryDate());
            visaRepository.delete(existingVisa);
            visaRepository.save(duplicatedVisa);
            return VisaAdapter.fromVisa(duplicatedVisa);
        }

        return VisaAdapter.fromVisa(existingVisa);
    }

    public void deleteVisa(String id) {
        Visa existingVisa = findVisaOrThrowException(id);
        visaRepository.delete(existingVisa);
    }
}
