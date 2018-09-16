package com.inoovalab.aaa.studentStatement.repository;

import com.inoovalab.aaa.studentStatement.model.Deposits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositsRepository extends JpaRepository<Deposits,Long> {
}
