package com.inoovalab.aaa.studentStatement.model;


import com.inoovalab.aaa.studentStatement.model.audit.UserDateAudit;

import javax.persistence.*;

@Entity
@Table(name = "expenseType")
public class ExpenseType extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String SubType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }
}
