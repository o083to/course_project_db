package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Назначение сотрудника на проект
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "project_assignments")
@IdClass(AssignmentPK.class)
public class ProjectAssignment {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "role", nullable = false)
    private String role;

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getRole() {
        return role;
    }
}
