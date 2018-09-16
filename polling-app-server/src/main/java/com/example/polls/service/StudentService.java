package com.inoovalab.aaa.studentStatement.service;

import com.inoovalab.aaa.studentStatement.exception.BadRequestException;
import com.inoovalab.aaa.studentStatement.model.Student;
import com.inoovalab.aaa.studentStatement.model.User;
import com.inoovalab.aaa.studentStatement.payload.PagedResponse;
import com.inoovalab.aaa.studentStatement.payload.StudentResponce;
import com.inoovalab.aaa.studentStatement.repository.MiscellaneousExpenseRepository;
import com.inoovalab.aaa.studentStatement.repository.StudentRepository;
import com.inoovalab.aaa.studentStatement.repository.SummeryExpenseRepository;
import com.inoovalab.aaa.studentStatement.repository.UserRepository;
import com.inoovalab.aaa.studentStatement.security.UserPrincipal;
import com.inoovalab.aaa.studentStatement.util.AppConstants;
import com.inoovalab.aaa.studentStatement.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SummeryExpenseRepository summeryExpenseRepository;
    @Autowired
    MiscellaneousExpenseRepository miscellaneousExpenseRepository;
    @Autowired
    private UserRepository userRepository;



    public PagedResponse<StudentResponce> getAllStudents(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Polls
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Student> students = studentRepository.findAll(pageable);

        if(students.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), students.getNumber(),
                    students.getSize(), students.getTotalElements(), students.getTotalPages(), students.isLast());
        }
        Map<Long, User> creatorMap = getStudentCreatorMap(students.getContent());
        List<StudentResponce> studentResponces = students.map(student -> {
            return ModelMapper.mapStudentToStudentResponse(student,

                    creatorMap.get(student.getCreatedBy()));
        }).getContent();


        return new PagedResponse<>(studentResponces, students.getNumber(),
                students.getSize(), students.getTotalElements(), students.getTotalPages(), students.isLast());
    }







    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
    Map<Long, User> getStudentCreatorMap(List<Student> students) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = students.stream()
                .map(Student::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }

}
