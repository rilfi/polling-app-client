package com.inoovalab.aaa.studentStatement.payload;

public class DepositRequest {

    private Long studentId;
    private Double amount;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
