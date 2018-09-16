package com.inoovalab.aaa.studentStatement.repository;

import com.inoovalab.aaa.studentStatement.model.SummeryExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummeryExpenseRepository extends JpaRepository<SummeryExpense,Long> {
}
