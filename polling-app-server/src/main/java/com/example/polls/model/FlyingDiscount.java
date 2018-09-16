package com.inoovalab.aaa.studentStatement.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="flyingDiscount" )
public class FlyingDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expenseType_id", nullable = false)
    private ExpenseType expenseType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    private  Double amount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aircrafType_id", nullable = false)
    private AircrafType aircrafType;
    @Temporal( TemporalType.DATE )
    private Date startDate;
    @Temporal( TemporalType.DATE )
    private Date endDate;

}
