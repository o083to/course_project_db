package models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anna on 23.11.14.
 */
public class AssignmentPK implements Serializable{
    private Employee employee;
    private Project project;
    private Date start_date;

    public AssignmentPK() { }

    public AssignmentPK(Employee employee, Project project, Date start_date) {
        this.employee = employee;
        this.project = project;
        this.start_date = start_date;
    }
}
