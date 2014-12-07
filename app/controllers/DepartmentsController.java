package controllers;

import models.BaseEntity;
import models.Department;
import models.Employee;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.data.Form;

import views.html.department.*;

import static play.data.Form.form;

/**
 * Created by anna on 07.12.14.
 */
public class DepartmentsController extends Controller {

    public static Result goHome() {
        return redirect(routes.DepartmentsController.departmentsList());
    }

    @Transactional(readOnly = true)
    public static Result departmentsList() {
        return ok(
                list.render()
        );
    }

    @Transactional(readOnly = true)
    public static Result create() {
        Form<Department> departmentForm = form(Department.class);
        return ok(
                createPage.render(departmentForm)
        );
    }

    @Transactional
    public static Result save() {
        Form<Department> departmentForm = form(Department.class).bindFromRequest();
        if (departmentForm.hasErrors()) {
            return badRequest(
                    createPage.render(departmentForm)
            );
        }
        departmentForm.get().save();
        return goHome();
    }

    @Transactional
    public static Result delete(long departmentId) {
        Department department = BaseEntity.findById(Department.class, departmentId);
        if (department != null) {
            department.delete();
        }
        return goHome();
    }

    @Transactional(readOnly = true)
    public static Result edit(long departmentId) {
        Department department = BaseEntity.findById(Department.class, departmentId);
        if (department == null) {
            return badRequest();
        } else {
            Form<Department> departmentForm = form(Department.class).fill(department);
            return ok(
                    editPage.render(departmentForm, departmentId)
            );
        }
    }

    @Transactional
    public static Result update(long departmentId) {
        Form<Department> departmentForm = form(Department.class).bindFromRequest();
        if (departmentForm.hasErrors()) {
            return badRequest(
                    editPage.render(departmentForm, departmentId)
            );
        }
        Department department = departmentForm.get();
        department.setId(departmentId);
        if (department.getManager() != null) {
            Employee employee = BaseEntity.findById(Employee.class, department.getManager().getId());
            department.setManager(employee);
        }
        department.update();
        return goHome();
    }
}
