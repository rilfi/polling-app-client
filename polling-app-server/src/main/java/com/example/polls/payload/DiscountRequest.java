package com.example.polls.payload;

import java.util.Date;

public class DiscountRequest {
    private Long studentReferenceNo;
    private Long expenseType_id;
    private Long amount;
    private Date startDate;
    private Date endDate;

    public Long getStudentReferenceNo() {
        return studentReferenceNo;
    }

    public void setStudentReferenceNo(Long studentReferenceNo) {
        this.studentReferenceNo = studentReferenceNo;
    }

    public Long getExpenseType_id() {
        return expenseType_id;
    }

    public void setExpenseType_id(Long expenseType_id) {
        this.expenseType_id = expenseType_id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
