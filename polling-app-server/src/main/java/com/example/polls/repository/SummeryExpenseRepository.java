package com.example.polls.repository;

import com.example.polls.model.ExpenseType;
import com.example.polls.model.Student;
import com.example.polls.model.SummeryExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummeryExpenseRepository extends JpaRepository<SummeryExpense,Long> {
    List<SummeryExpense> findAllByStudent_Id(Long studentId);
    SummeryExpense findByStudentIdAndExpenseTypeId(Long studentId,Long expenseTypeId);
}
