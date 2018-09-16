package com.example.polls.payload;

import com.example.polls.model.audit.UserDateAudit;

public class DynamicExpenseRequest extends UserDateAudit {

    private Long expenseTypeId;
    private  Double amount;
    private Long studentId;

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
