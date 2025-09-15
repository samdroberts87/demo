package com.example.visa_movement_tracker.repositories;

import com.example.visa_movement_tracker.entities.TravelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelHistoryRepository  extends JpaRepository<TravelHistory, Long> {
    List<TravelHistory> findByPersonId(Long personId);
}
