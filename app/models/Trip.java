package models;

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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_seq")
    @Column(name = "trip_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "location", length = 100, nullable = false)
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }
}
