package com.example.visa_movement_tracker.services;

import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.exceptions.person.InvalidPersonAttributeException;
import com.example.visa_movement_tracker.exceptions.person.InvalidPersonException;
import com.example.visa_movement_tracker.exceptions.person.PersonNotFoundException;
import com.example.visa_movement_tracker.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service()
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public boolean isIncompletePersonValid(Person person) {
        return Stream.of(
                person.getFirstName() == null || person.isFirstNameValid(),
                person.getLastName() == null || person.isLastNameValid(),
                person.getDateOfBirth() == null || person.isDateOfBirthValid(),
                person.getAddressLine1() == null || person.isAddressLine1Valid(),
                person.getPostcode() == null || person.isPostcodeValid(),
                person.getEmail() == null || person.isEmailValid()
        ).allMatch(bool -> bool == true);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person create(Person person) {
        if (!person.isPersonValid()) throw new InvalidPersonException("Person's details are invalid.");
        return repository.save(person);
    }

    public Optional<Person> findPersonByUnvalidatedId(Long id) {
        if (!Person.isIdValid(id)) throw new InvalidPersonAttributeException("ID " + id + " is invalid");
        return repository.findById(id);
    }

    // Searches the repository based on the not-null values in the passed Person
    public List<Person> findPersonByDetails(Person person) {
        if (!isIncompletePersonValid(person))
            throw new InvalidPersonAttributeException("Person's details are invalid.");
        Example<Person> example = Example.of(person);
        return repository.findAll(example);
    }

    // Requires person's id to be set for it to work
    // Gets the person from the repository matching the id of the passed person, replaces the person in the repository
    public Person updatePersonByDetails(Person person) {
        Optional<Person> optionalPerson = findPersonByUnvalidatedId(person.getId());
        if (optionalPerson.isEmpty()) throw new PersonNotFoundException("No person matching provided ID.");
        Person existingPerson = optionalPerson.get();
        if (person.getFirstName() != null) existingPerson.setFirstName(person.getFirstName());
        if (person.getLastName() != null) existingPerson.setLastName(person.getLastName());
        if (person.getDateOfBirth() != null) existingPerson.setDateOfBirth(person.getDateOfBirth());
        if (person.getAddressLine1() != null) existingPerson.setAddressLine1(person.getAddressLine1());
        if (person.getPostcode() != null) existingPerson.setPostcode(person.getPostcode());
        if (person.getEmail() != null) existingPerson.setEmail(person.getEmail());

        return existingPerson;
    }

    public boolean deletePerson(Long id) {
        Optional<Person> existingPerson = findPersonByUnvalidatedId(id);
        if (existingPerson.isPresent()) {
            repository.delete(existingPerson.get());
            return true;
        }
        else return false;

    }
}
