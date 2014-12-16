package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Проект
 * Created by anna on 08.11.14.
 */

@Entity
@Table(name = "projects")
@SequenceGenerator(name = "projects_seq", sequenceName = "projects_seq")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_seq")
    @Column(name = "project_id")
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    @Constraints.Required
    private String name;

    @Column(name = "start_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "cost", nullable = false)
    @Constraints.Required
    private long cost;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectAssignment> assignments;

    private static final String SQL_GET_ALL_PROJECTS = "select * from projects order by name";
    private static final String SQL_GET_PROJECTS_WITH_FILTER = "select p.* \n" +
                                                                "from \n" +
                                                                "projects p, customers c \n" +
                                                                "where p.customer_id = c.customer_id \n" +
                                                                "and upper(p.name) like upper(:projectName) \n" +
                                                                "and upper(c.name) like upper(:customerName) \n" +
                                                                "order by p.start_date desc";

    private static final String SQL_GET_EMPLOYEES =
            "select e.*\n" +
            "from employees e\n" +
            ",project_assignments pa\n" +
            "where\n" +
            "pa.project_id = :projectId\n" +
            "and pa.employee_id = e.employee_id";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProjectAssignment> getAssignments() {
        return assignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public static Map<String, String> getAllProjectsAsOptions() {
        Query query = JPA.em().createNativeQuery(SQL_GET_ALL_PROJECTS, Project.class);
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        List<Project> projects = query.getResultList();
        for (Project project : projects) {
            options.put(String.valueOf(project.getId()), project.getName());
        }
        return options;
    }

    public static List<Project> getList(String projectName, String customerName) {
        return JPA.em().createNativeQuery(SQL_GET_PROJECTS_WITH_FILTER, Project.class)
                .setParameter("projectName", formatForLike(projectName))
                .setParameter("customerName", formatForLike(customerName))
                .getResultList();
    }

    // todo: show assignments
    public List<Employee> getEmployees() {
        return JPA.em().createNativeQuery(SQL_GET_EMPLOYEES, Employee.class)
                .setParameter("projectId", id)
                .getResultList();
    }
}
