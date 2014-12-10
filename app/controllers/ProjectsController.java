package controllers;

import models.Customer;
import models.Project;
import play.mvc.Controller;

import models.BaseEntity;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.project.*;

import static play.data.Form.form;

/**
 * Created by anna on 07.12.14.
 */
public class ProjectsController extends Controller {

    public static Result goHome() {
        return redirect(
                routes.ProjectsController.projectsList("", "")
        );
    }

    @Transactional(readOnly = true)
    public static Result projectsList(String projectName, String customerName) {
        return ok(
                list.render(projectName, customerName)
        );
    }

    @Transactional(readOnly = true)
    public static Result create() {
        Form<Project> projectForm = form(Project.class);
        return ok(
                createPage.render(projectForm)
        );
    }

    @Transactional
    public static Result save() {
        Form<Project> projectForm = form(Project.class).bindFromRequest();
        if (projectForm.hasErrors()) {
            return badRequest(
                    createPage.render(projectForm)
            );
        }
        Project project = projectForm.get();
        Customer customer = BaseEntity.findById(Customer.class, project.getCustomer().getId());
        project.setCustomer(customer);
        project.save();
        return goHome();
    }

    @Transactional
    public static Result delete(long projectId) {
        Project project = BaseEntity.findById(Project.class, projectId);
        if (project != null) {
            project.delete();
        }
        return goHome();
    }

    @Transactional(readOnly = true)
    public static Result edit(long projectId) {
        Project project = BaseEntity.findById(Project.class, projectId);
        if (project == null) {
            return badRequest();
        } else {
            Form<Project> projectForm = form(Project.class).fill(project);
            return ok(
                    editPage.render(projectForm, projectId)
            );
        }
    }

    @Transactional
    public static Result update(long projectId) {
        Form<Project> projectForm = form(Project.class).bindFromRequest();
        if (projectForm.hasErrors()) {
            return badRequest(
                    editPage.render(projectForm, projectId)
            );
        }
        Project project = projectForm.get();
        project.setId(projectId);
        Customer customer = BaseEntity.findById(Customer.class, project.getCustomer().getId());
        project.setCustomer(customer);
        project.update();
        return goHome();
    }

    @Transactional(readOnly = true)
    public static Result employeesList(long projectId) {
        Project project = BaseEntity.findById(Project.class, projectId);
        if (project == null) {
            return badRequest();
        } else {
            return ok(
                    employees.render(project)
            );
        }
    }
}
