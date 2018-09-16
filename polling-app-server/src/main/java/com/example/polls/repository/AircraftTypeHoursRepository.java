package com.example.polls.repository;

import com.example.polls.model.AircraftTypeHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftTypeHoursRepository   extends JpaRepository<AircraftTypeHours,Long> {

    AircraftTypeHours findByStudent_Id(Long studentId);
}
