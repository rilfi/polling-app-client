package com.example.polls.model;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "flyingHours")
public class FlyingHours {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "aircraftType")
    private String aircraftType;

    @NotNull
    @Column(name = "duration")
    private Double duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public Double getDuration() {
        return duration;
    }
}
