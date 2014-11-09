package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Отпуск
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "vacations")
@SequenceGenerator(name = "vacations_seq", sequenceName = "vacations_seq")
public class Vacation {

    private long id;
    private Employee employee;
    private Date startDate;
    private Date endDate;
    // todo: Enum?
    private String reason;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacations_seq")
    @Column(name = "vacation_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    @Column(name = "reason", length = 20, nullable = false)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
