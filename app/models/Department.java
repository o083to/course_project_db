package models;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Отдел
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "departments")
@SequenceGenerator(name = "departments_seq", sequenceName = "departments_seq")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_seq")
    @Column(name = "department_id")
    private long id;

    @Column(name = "name", length = 250, nullable = false)
    @Constraints.Required
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
    private Employee manager;

    private static final String SQL_GET_DEPARTMENTS = "select * from departments order by name";
    private static final String SQL_GET_ALL_EMPLOYEES = "select * from employees where department_id = :departmentId";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Department> getList() {
        return JPA.em().createNativeQuery(SQL_GET_DEPARTMENTS, Department.class)
                .getResultList();
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public static Map<String, String> getEmployeesAsOptions(long departmentId) {
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        List<Employee> employees = JPA.em().createNativeQuery(SQL_GET_ALL_EMPLOYEES, Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
        for (Employee employee : employees) {
            options.put(
                    String.valueOf(employee.getId()),
                    employee.getLastName() + " " + employee.getFirstName()
            );
        }
        return options;
    }
}
