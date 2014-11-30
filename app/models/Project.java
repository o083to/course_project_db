package models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Проект
 * Created by anna on 08.11.14.
 */

@Entity
@Table(name = "projects")
@SequenceGenerator(name = "projects_seq", sequenceName = "projects_seq")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_seq")
    @Column(name = "project_id")
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

//    private Date startDate;
//    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<ProjectAssignment> assignments;

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

    public Customer getCustomer() {
        return customer;
    }

//    @Column(name = "start_date", nullable = false)
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    @Column(name = "end_date", nullable = false)
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
}
