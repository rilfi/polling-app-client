package com.example.polls.repository;

import com.example.polls.model.FlyingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlyingHoursRepository  extends JpaRepository<FlyingHours,String> {

    List<FlyingHours> findAllByName(String name);
}
