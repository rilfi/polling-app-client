package com.inoovalab.aaa.studentStatement.payload;

import com.inoovalab.aaa.studentStatement.model.User;
import org.hibernate.annotations.NaturalId;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StudentRequest {
    private Long id;
    private String name;
    private String indexNo;
    private String email;
}
