package com.example.polls.repository;

import com.example.polls.model.DynamicExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicExpenseRepository extends JpaRepository<DynamicExpense,Long> {
}
