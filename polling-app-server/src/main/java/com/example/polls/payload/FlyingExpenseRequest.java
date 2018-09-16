package com.example.polls.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlyingExpenseRequest {

    private Long studentReference;
    private String studentName;
    private String aircraftType;
    private String date;
    private Double duration;

    public Long getStudentReference() {
        return studentReference;
    }

    public void setStudentReference(Long studentReference) {
        this.studentReference = studentReference;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FlyingExpenseRequest{" +
                "studentReference=" + studentReference +
                ", studentName='" + studentName + '\'' +
                ", aircraftType='" + aircraftType + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                '}';
    }
}
