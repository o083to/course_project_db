package models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import play.db.jpa.JPA;

/**
 * Сотрудник
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "employees")
@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @Column(name = "employee_id")
    private long id;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 50, nullable = true)
    private String middleName;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "position", length = 50, nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Salary> salaries;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Vacation> vacations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Trip> trips;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<ProjectAssignment> assignments;

    private static final PeriodFormatter PERIOD_FORMATTER = new PeriodFormatterBuilder()
            .appendPrefix("Лет: ")
            .appendYears()
            .appendPrefix(" Месяцев: ")
            .appendMonths()
            .appendPrefix(" Дней: ")
            .appendDays()
            .toFormatter();

    private static final String SQL_GET_MANAGER_ID =
            "select\n" +
               "m.employee_id\n" +
            "from\n" +
               "employees e\n" +
               ",employees m\n" +
               ",departments d\n" +
            "where\n" +
               "e.employee_id = :employee_id\n" +
               "and d.department_id = e.department_id\n" +
               "and m.employee_id = d.manager_id";

    @Transient
    private String experienceStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public String getPosition() {
        return position;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public List<Vacation> getVacations() {
        return vacations;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<ProjectAssignment> getAssignments() {
        return assignments;
    }

    public static Employee findById(long id) {
        return JPA.em().find(Employee.class, id);
    }

    public String getExperienceStr() {
        if (experienceStr == null) {
            experienceStr = PERIOD_FORMATTER.print(new Period(
                    new LocalDate(hireDate)
                    ,new LocalDate()
                    ,PeriodType.yearMonthDay()
            ));
        }
        return experienceStr;
    }

    public Employee getManager() {
        Query query = JPA.em().createNativeQuery(SQL_GET_MANAGER_ID, Employee.class);
        query.setParameter("employee_id", id);
        return (Employee)query.getSingleResult();
    }
}
