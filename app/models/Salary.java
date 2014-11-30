package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;

/**
 * Зарплата
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "salary")
@SequenceGenerator(name = "salary_seq", sequenceName = "salary_seq")
public class Salary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salary_seq")
    @Column(name = "salary_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "value", nullable = false)
    @Constraints.Required
    private int value;

    @Column(name = "start_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date startDate;

    @Column(name = "end_date", nullable = true)
    private Date endDate;

    private static final String SET_LAST_END_DATE =
            "update salary\n" +
            "set end_date = :end_date\n" +
            "where end_date is null";

    private static final String DELETE_LAST_END_DATE =
            "update SALARY\n" +
            "set end_date = null\n" +
            "where \n" +
            "   employee_id = :emp_id\n" +
            "   and end_date = (\n" +
            "       select\n" +
            "           max(end_date)\n" +
            "       from\n" +
            "           salary\n" +
            "       where\n" +
            "           employee_id = :emp_id\n" +
            "       )";

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public static void setLastEndDate(Date endDate, long employeeId) {
        Query query = JPA.em().createNativeQuery(SET_LAST_END_DATE);
        query.setParameter("end_date", endDate);
        query.executeUpdate();
    }

    public static void deleteLastEndDate(long employeeId) {
        Query query = JPA.em().createNativeQuery(DELETE_LAST_END_DATE);
        query.setParameter("emp_id", employeeId);
        query.executeUpdate();
    }

}
