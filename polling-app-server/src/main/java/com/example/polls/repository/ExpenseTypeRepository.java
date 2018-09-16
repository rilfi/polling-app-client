package com.example.polls.repository;

import com.example.polls.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType,Long> {
    List<ExpenseType> findAllByType(String type);
    ExpenseType findByTypeAndSubType(String type,String subType);
    ExpenseType findByType(String type);

}
