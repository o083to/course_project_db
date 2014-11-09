package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Зарплата
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "salary")
@SequenceGenerator(name = "salary_seq", sequenceName = "salary_seq")
public class Salary {

    private long id;
    private Employee employee;
    private int value;
    private Date startDate;
    private Date endDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq")
    @Column(name = "salary_id")
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

    @Column(name = "value", nullable = false)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
