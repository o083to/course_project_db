package models.entity;

import javax.persistence.*;
import java.util.Date;

import play.db.jpa.JPA;

/**
 * Сотрудник
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "employees")
@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq")
public class EmployeeEntity {

    private long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Date hireDate;
    private String position;
    private DepartmentEntity department;
//    private List<Salary> salaries;
//    private List<Trip> trips;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @Column(name = "employee_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "last_name", length = 50, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "first_name", length = 50, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name", length = 50, nullable = true)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "hire_date", nullable = false)
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Column(name = "position", length = 50, nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee_id")
//    public List<Salary> getSalaries() {
//        return salaries;
//    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee_id")
//    public List<Trip> getTrips() {
//        return trips;
//    }

    public static EmployeeEntity findById(long id) {
        return JPA.em().find(EmployeeEntity.class, id);
    }
}
