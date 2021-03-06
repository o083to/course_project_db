package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Назначение сотрудника на проект
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "project_assignments")
@SequenceGenerator(name = "assignment_seq", sequenceName = "assignment_seq")
public class ProjectAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assignment_seq")
    @Column(name = "assignment_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "start_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date endDate;

    @Column(name = "role", nullable = false)
    @Constraints.Required
    private String role;

    private static final Map<String, String> ROLES = new HashMap<String, String>(){{
        put("Руководитель проекта", "Руководитель проекта");
        put("Аналитик", "Аналитик");
        put("Системный инженер", "Системный инженер");
        put("Руководитель группы разработки", "Руководитель группы разработки");
        put("Старший разработчик", "Старший разработчик");
        put("Разработчик", "Разработчик");
        put("Младший разработчик", "Младший разработчик");
        put("Руководитель группы тестирования", "Руководитель группы тестирования");
        put("Тестировщик", "Тестировщик");
    }};

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Map<String, String> getRolesAsOptions() {
        return ROLES;
    }
}
