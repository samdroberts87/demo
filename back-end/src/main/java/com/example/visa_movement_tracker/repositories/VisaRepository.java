package com.example.visa_movement_tracker.repositories;

import com.example.visa_movement_tracker.entities.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisaRepository extends JpaRepository<Visa, String> {
    List<Visa> findByPersonId(Long personId);
}
