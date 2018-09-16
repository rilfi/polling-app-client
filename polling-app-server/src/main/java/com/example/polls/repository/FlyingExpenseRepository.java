package com.example.polls.repository;

import com.example.polls.model.FlyingCount;
import com.example.polls.model.FlyingExpense;
import com.example.polls.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlyingExpenseRepository extends JpaRepository<FlyingExpense,Long> {
   //
   // @Query("SELECT NEW com.example.polls.model.FlyingCount(f.student.id,f.expenseType.id,f.rate,f.discount,sum(f.duration),sum (f.amount)) FROM FlyingExpense f GROUP BY f.student.id");

    @Query("SELECT NEW com.example.polls.model.FlyingCount(v.student.id,v.expenseType.subType,v.rate,v.discount,SUM (v.duration),SUM (v.amount)) FROM FlyingExpense v WHERE v.student.id = :studentId GROUP BY v.expenseType.id , v.rate,v.discount")
    List<FlyingCount>getByStudentAndExpenseTypeAndRateAndDiscount(@Param("studentId") Long studentId);
    //List<FlyingExpense> findByDateAndStudentAndAircraft_IdAndInstructorId(LocalDate localDate, Student student,Long aircraftId,Long instructorId);
    List<FlyingExpense> findByStudentId(Long studentId);



}
