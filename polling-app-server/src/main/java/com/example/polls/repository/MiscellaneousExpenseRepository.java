package com.example.polls.repository;

import com.example.polls.model.MiscellaneousExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiscellaneousExpenseRepository extends JpaRepository<MiscellaneousExpense,Long> {

    List<MiscellaneousExpense> findAllByStudent_Id(Long studentId);

}
