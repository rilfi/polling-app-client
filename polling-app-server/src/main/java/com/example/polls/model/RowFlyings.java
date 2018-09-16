package com.example.polls.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class RowFlyings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private Long studentReference;
    private String aircraftName;
    private String aircraftTYpe;
    private Long instructorId;
    private Long insructorName;
    @Basic
    private LocalDate date;
    @Basic
    private LocalTime offBlock;
    @Basic
    private LocalTime onBlock;
    private Double duration;
}
