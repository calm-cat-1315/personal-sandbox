package com.personalsandbox.workmanagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "work_hour_records")
public class WorkHourRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "hours", nullable = false, precision = 5, scale = 2)
    private BigDecimal hours;

    @Column(name = "label")
    private String label;

    @Column(name = "note")
    private String note;

    protected WorkHourRecord() {
    }

    public WorkHourRecord(LocalDate workDate, BigDecimal hours, String label, String note) {
        this.workDate = workDate;
        this.hours = hours;
        this.label = label;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public String getLabel() {
        return label;
    }

    public String getNote() {
        return note;
    }
}
