package models.entity;

import models.entity.EmployeeEntity;

import javax.persistence.*;

/**
 * Отдел
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "departments")
@SequenceGenerator(name = "departments_seq", sequenceName = "departments_seq")
public class DepartmentEntity {

    private long id;
    private String name;
//    private EmployeeEntity manager;
//    private Department mainDepartment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_seq")
    @Column(name = "department_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", length = 100, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
//    public EmployeeEntity getManager() {
//        return manager;
//    }
//
//    public void setManager(EmployeeEntity manager) {
//        this.manager = manager;
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "main_department_id", referencedColumnName = "department_id")
//    public Department getMainDepartment() {
//        return mainDepartment;
//    }
//
//    public void setMainDepartment(Department mainDepartment) {
//        this.mainDepartment = mainDepartment;
//    }
}
