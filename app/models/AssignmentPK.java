package models;

import java.io.Serializable;

/**
 * Created by anna on 23.11.14.
 */
public class AssignmentPK implements Serializable{
    private Employee employee;
    private Project project;

    public AssignmentPK() { }

    public AssignmentPK(Employee employee, Project project) {
        this.employee = employee;
        this.project = project;
    }
}
