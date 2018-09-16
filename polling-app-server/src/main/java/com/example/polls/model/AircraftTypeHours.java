package com.example.polls.model;

import javax.persistence.*;

@Entity
public class AircraftTypeHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;
    @Column(name="c150")
    private Double c150=0.0;
    @Column(name="c172rg")
    private Double c172rg=0.0;
    @Column(name="pa34")
    private Double pa34=0.0;
    @Column(name="c172n")
    private Double c172n=0.0;
    @Column(name="c152")
    private Double c152=0.0;
    @Column(name="frasca141")
    private Double frasca141=0.0;
    @Column(name="eliteSingle")
    private Double eliteSingle=0.0;
    @Column(name="eliteTwin")
    private Double eliteTwin=0.0;
    @Column(name="total")
    private Double total=0.0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getC150() {
        return c150;
    }

    public void setC150(Double c150) {
        this.c150 = c150;
    }

    public Double getC172rg() {
        return c172rg;
    }

    public void setC172rg(Double c172rg) {
        this.c172rg = c172rg;
    }

    public Double getPa34() {
        return pa34;
    }

    public void setPa34(Double pa34) {
        this.pa34 = pa34;
    }

    public Double getC172n() {
        return c172n;
    }

    public void setC172n(Double c172n) {
        this.c172n = c172n;
    }

    public Double getC152() {
        return c152;
    }

    public void setC152(Double c152) {
        this.c152 = c152;
    }

    public Double getFrasca141() {
        return frasca141;
    }

    public void setFrasca141(Double frasca141) {
        this.frasca141 = frasca141;
    }

    public Double getEliteSingle() {
        return eliteSingle;
    }

    public void setEliteSingle(Double eliteSingle) {
        this.eliteSingle = eliteSingle;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getEliteTwin() {
        return eliteTwin;
    }

    public void setEliteTwin(Double eliteTwin) {
        this.eliteTwin = eliteTwin;
    }
}
