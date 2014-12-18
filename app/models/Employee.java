package models;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * Сотрудник
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "employees")
@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @Column(name = "employee_id")
    private long id;

    @Column(name = "last_name", length = 50, nullable = false)
    @Constraints.Required
    private String lastName;

    @Column(name = "first_name", length = 50, nullable = false)
    @Constraints.Required
    private String firstName;

    @Column(name = "middle_name", length = 50, nullable = false)
    @Constraints.Required
    private String middleName;

    @Column(name = "hire_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date hireDate;

    @Column(name = "position", length = 250, nullable = false)
    @Constraints.Required
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

    private static final Map<String, String> POSITIONS_LIST = new LinkedHashMap<String, String>(){{
        put("Руководитель проекта", "Руководитель проекта");
        put("Руководитель группы разработки", "Руководитель группы разработки");
        put("Старший разработчик", "Старший разработчик");
        put("Разработчик", "Разработчик");
        put("Младший разработчик", "Младший разработчик");
        put("Аналитик", "Аналитик");
        put("Руководитель группы тестирования", "Руководитель группы тестирования");
        put("Старший тестировщик", "Старший тестировщик");
        put("Тестировщик", "Тестировщик");
        put("Младший тестировщик", "Младший тестировщик");
    }};

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
               "m.*\n" +
            "from\n" +
               "employees e\n" +
               ",employees m\n" +
               ",departments d\n" +
            "where\n" +
               "e.employee_id = :employee_id\n" +
               "and d.department_id = e.department_id\n" +
               "and m.employee_id = d.manager_id";

    private static final String FIRE_EMPLOYEE = "begin terminate(:empId); end;";

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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        try {
            Query query = JPA.em().createNativeQuery(SQL_GET_MANAGER_ID, Employee.class);
            query.setParameter("employee_id", id);
            return (Employee)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Map<String, String> getPositionsAsOptions() {
        return POSITIONS_LIST;
    }

    public void terminate() {
        JPA.em().createNativeQuery(FIRE_EMPLOYEE)
                .setParameter("empId", id)
                .executeUpdate();
    }
}
