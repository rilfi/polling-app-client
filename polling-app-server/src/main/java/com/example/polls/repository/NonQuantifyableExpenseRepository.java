package com.inoovalab.aaa.studentStatement.repository;

import com.inoovalab.aaa.studentStatement.model.NonQuantifyableExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonQuantifyableExpenseRepository extends JpaRepository<NonQuantifyableExpense,Long> {
}
