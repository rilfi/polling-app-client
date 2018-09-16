package com.example.polls.repository;

import com.example.polls.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByName(String name);

    Student findByReferenceNo(Long referenceNo);


}
