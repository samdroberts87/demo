package com.example.visa_movement_tracker.repositories;

import com.example.visa_movement_tracker.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{}
