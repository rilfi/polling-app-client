package com.inoovalab.aaa.studentStatement.repository;

import com.inoovalab.aaa.studentStatement.model.QuantifiableExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantifiableExpenseRepository extends JpaRepository<QuantifiableExpense,Long> {
}
