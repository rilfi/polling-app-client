package com.inoovalab.aaa.studentStatement.repository;

import com.inoovalab.aaa.studentStatement.model.MiscellaneousExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiscellaneousExpenseRepository extends JpaRepository<MiscellaneousExpense,Long> {

}
