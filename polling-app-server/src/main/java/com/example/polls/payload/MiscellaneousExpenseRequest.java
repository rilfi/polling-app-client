package com.example.polls.payload;

public class MiscellaneousExpenseRequest {


    private String expenseName;
    private  Double amount;



    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
