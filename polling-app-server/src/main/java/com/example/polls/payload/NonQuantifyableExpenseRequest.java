package com.inoovalab.aaa.studentStatement.payload;

import javax.validation.constraints.NotNull;

public class NonQuantifyableExpenseRequest {
    @NotNull
    private Long expenseTypeId;

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
