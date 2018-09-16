package com.inoovalab.aaa.studentStatement.payload;

public class QuantifiableExpenseRequest {
    private Long expenseTypeId;
    private Integer quantity;

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
