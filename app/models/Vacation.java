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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacations_seq")
    @Column(name = "vacation_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    // todo: Enum?
    @Column(name = "reason", length = 50, nullable = false)
    private String reason;

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

    public String getReason() {
        return reason;
    }
}
