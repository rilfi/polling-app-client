package com.example.polls.repository;

import com.example.polls.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
     @Query("SELECT v FROM Discount v WHERE v.student.id = :studentId AND v.expenseType.id = :expenseId AND :d1 BETWEEN v.startDate AND v.endDate")
     Discount getByStudentIdAndExpenseTypeIdAndStartDateBeforeAndEndDateAfter(@Param("studentId")Long studentId,
                                                                               @Param("expenseId")Long expenseId,
                                                                               @Param("d1")Date d1);

}
