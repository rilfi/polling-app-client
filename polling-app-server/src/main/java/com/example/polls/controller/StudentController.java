package com.inoovalab.aaa.studentStatement.controller;

import com.inoovalab.aaa.studentStatement.payload.PagedResponse;
import com.inoovalab.aaa.studentStatement.payload.StudentResponce;
import com.inoovalab.aaa.studentStatement.security.CurrentUser;
import com.inoovalab.aaa.studentStatement.security.UserPrincipal;
import com.inoovalab.aaa.studentStatement.service.StudentService;
import com.inoovalab.aaa.studentStatement.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping
    public PagedResponse<StudentResponce> getStudents(@CurrentUser UserPrincipal currentUser,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return studentService.getAllStudents(currentUser, page, size);
    }
}
