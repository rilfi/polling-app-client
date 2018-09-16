package com.example.polls.repository;

import com.example.polls.model.NonQuantifyableExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonQuantifyableExpenseRepository extends JpaRepository<NonQuantifyableExpense,Long> {
}
