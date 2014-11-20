package models;

import models.entity.EmployeeEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Командировка
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "trips")
@SequenceGenerator(name = "trips_seq", sequenceName = "trips_seq")
public class Trip {

    private long id;
    private EmployeeEntity employeeEntity;
    private Date startDate;
    private Date endDate;
    private String location;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_seq")
    @Column(name = "trip_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "location", length = 100, nullable = false)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
