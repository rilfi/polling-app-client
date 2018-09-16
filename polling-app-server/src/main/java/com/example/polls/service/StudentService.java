package com.example.polls.service;


import com.example.polls.exception.BadRequestException;
import com.example.polls.model.*;
import com.example.polls.payload.*;
import com.example.polls.repository.*;
import com.example.polls.security.UserPrincipal;
import com.example.polls.util.AppConstants;
import com.example.polls.util.ModelMapper;
import org.hibernate.tool.hbm2ddl.SchemaExportTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private MiscellaneousExpenseRepository miscellaneousExpenseRepository;
    @Autowired
    private SummeryExpenseRepository summeryExpenseRepository;



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

    public Student createStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setReferenceNo(studentRequest.getReferenceNo());
        student.setName(studentRequest.getName());
        student.setEmail(studentRequest.getEmail());
        student.setContactNo(studentRequest.getContactNo());





        return studentRepository.save(student);
    }

    public Double getDiscount(Long studentId, Long expenseTypeID, Date date){



return null;


    }

    public ExpenseType createExpenseType(ExpenseTypeRequest expenseTypeRequest) {
        ExpenseType expenseType = new ExpenseType();
        expenseType.setType(expenseTypeRequest.getType());
        expenseType.setSubType(expenseTypeRequest.getSubType());
        expenseType.setCharge(expenseTypeRequest.getCharge());

        return expenseTypeRepository.save(expenseType);
    }

    public List<ExpenseTypeResponce>getExpensetypes(){

        List<ExpenseType>expenseTypes=expenseTypeRepository.findAll();


        List<ExpenseTypeResponce> expenseTypeResponces = expenseTypes.stream().map(expenseType ->
        {
            ExpenseTypeResponce obj = new ExpenseTypeResponce();
            obj.setId(expenseType.getId());
            obj.setType(expenseType.getType());
            obj.setSubType(expenseType.getSubType());
            obj.setCharge(expenseType.getCharge());
            return obj;
        }).collect(Collectors.toList());


        return  expenseTypeResponces;

    }




    public List<SummeryExpenseResponce>getSummeryExpense(Long studentId){

        List<SummeryExpense>summeryExpenses=summeryExpenseRepository.findAllByStudent_Id(studentId);


        List<SummeryExpenseResponce> summeryExpenseResponces = summeryExpenses.stream().map(summeryExpense ->
            ModelMapper.mapSummeryExpenseToSummeryExpenseResponse(summeryExpense)).collect(Collectors.toList());


        return  summeryExpenseResponces;

    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
