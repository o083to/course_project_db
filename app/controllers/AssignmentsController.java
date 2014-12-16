package controllers;

import models.BaseEntity;
import models.Employee;
import models.Project;
import models.ProjectAssignment;
import play.mvc.Controller;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;
import views.html.assignment.*;

import static play.data.Form.form;

/**
 * Created by anna on 04.12.14.
 */

@Security.Authenticated(Authorization.class)
public class AssignmentsController extends Controller {

    public static Result goHome(long employeeId) {
        return redirect(routes.EmployeeController.employeeProjects(employeeId));
    }

    @Transactional(readOnly = true)
    public static Result create(long employeeId) {
        Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class);
        return ok(
                createPage.render(assignmentForm, employeeId)
        );
    }

    @Transactional
    public static Result save(long employeeId) {
        Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class).bindFromRequest();
        if (assignmentForm.hasErrors()) {
            return badRequest(
                    createPage.render(assignmentForm, employeeId)
            );
        }
        ProjectAssignment assignment = assignmentForm.get();
        Employee employee = Employee.findById(Employee.class, employeeId);
        assignment.setEmployee(employee);
        Project project = BaseEntity.findById(Project.class, assignment.getProject().getId());
        assignment.setProject(project);
        assignment.save();
        return goHome(employeeId);
    }

    @Transactional
    public static Result delete(long employeeId, long assignmentId) {
        ProjectAssignment assignment = BaseEntity.findById(ProjectAssignment.class, assignmentId);
        if (assignment != null) {
            assignment.delete();
        }
        return goHome(employeeId);
    }

    @Transactional(readOnly = true)
    public static Result edit(long employeeId, long assignmentId) {
        ProjectAssignment assignment = BaseEntity.findById(ProjectAssignment.class, assignmentId);
        if (assignment == null) {
            return badRequest();
        } else {
            Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class).fill(assignment);
            return ok(
                    editPage.render(assignmentForm, employeeId, assignmentId)
            );
        }
    }

    @Transactional
    public static Result update(long employeeId, long assignmentId) {
        Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class).bindFromRequest();
        if (assignmentForm.hasErrors()) {
            return badRequest(
                    editPage.render(assignmentForm, employeeId, assignmentId)
            );
        }
        ProjectAssignment assignment = assignmentForm.get();
        assignment.setId(assignmentId);
        Employee employee = Employee.findById(Employee.class, employeeId);
        assignment.setEmployee(employee);
        Project project = Project.findById(Project.class, assignment.getProject().getId());
        assignment.setProject(project);
        assignment.update();
        return goHome(employeeId);
    }
}
