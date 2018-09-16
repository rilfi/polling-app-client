package com.inoovalab.aaa.studentStatement.repository;


import com.inoovalab.aaa.studentStatement.model.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance,Long> {
}
