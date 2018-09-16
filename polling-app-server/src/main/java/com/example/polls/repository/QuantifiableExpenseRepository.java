package com.example.polls.repository;

import com.example.polls.model.QuantifiableExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantifiableExpenseRepository extends JpaRepository<QuantifiableExpense,Long> {
}
