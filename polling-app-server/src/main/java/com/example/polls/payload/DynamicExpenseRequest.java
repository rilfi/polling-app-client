package com.inoovalab.aaa.studentStatement.payload;

import com.inoovalab.aaa.studentStatement.model.audit.UserDateAudit;

public class DynamicExpenseRequest extends UserDateAudit {

    private Long expenseTypeId;
    private  Double amount;

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
}
