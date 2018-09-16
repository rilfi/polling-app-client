package com.example.polls.model;

public class FlyingCount {

    private Long  studentId;
    private String subType;
    private Double rate;
    private Double discount;
    private Double durationCount;
    private Double sumAmount;


    public FlyingCount(Long studentId, String subType, Double rate, Double discount, Double durationCount, Double sumAmount) {
        this.studentId = studentId;
        this.subType = subType;
        this.rate = rate;
        this.discount = discount;
        this.durationCount = durationCount;
        this.sumAmount = sumAmount;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDurationCount() {
        return durationCount;
    }

    public void setDurationCount(Double durationCount) {
        this.durationCount = durationCount;
    }

    public Double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Double sumAmount) {
        this.sumAmount = sumAmount;
    }
}
