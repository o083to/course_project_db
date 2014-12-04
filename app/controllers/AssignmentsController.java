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

import static play.data.Form.form;

/**
 * Created by anna on 04.12.14.
 */
public class AssignmentsController extends Controller {

    public static Result goHome(long employeeId) {
        return redirect(routes.EmployeeController.employeeProjects(employeeId));
    }

    @Transactional(readOnly = true)
    public static Result create(long employeeId) {
        Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class);
        return ok(
                create_assignment_form.render(assignmentForm, employeeId)
        );
    }

    @Transactional
    public static Result save(long employeeId) {
        Form<ProjectAssignment> assignmentForm = form(ProjectAssignment.class).bindFromRequest();
        if (assignmentForm.hasErrors()) {
            return badRequest(
                    create_assignment_form.render(assignmentForm, employeeId)
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
}
