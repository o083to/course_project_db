package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;

/**
 * Командировка
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "trips")
@SequenceGenerator(name = "trips_seq", sequenceName = "trips_seq")
public class Trip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_seq")
    @Column(name = "trip_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date endDate;

    @Column(name = "location", length = 100, nullable = false)
    @Constraints.Required
    private String location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
