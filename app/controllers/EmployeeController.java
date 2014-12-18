package controllers;

import models.BaseEntity;
import models.Department;
import models.Employee;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.data.Form;

import views.html.*;
import views.html.employee.*;

import static play.data.Form.form;

@Security.Authenticated(Authorization.class)
public class EmployeeController extends Controller {

    public static Result goHome(long departmentId) {
        return redirect(
                routes.DepartmentsController.employees(departmentId, "", "")
        );
    }

    @Transactional(readOnly=true)
    public static Result employeeInfo(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(infoPage.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeSalaries(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(salaryList.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeVacations(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(vacationsList.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeTrips(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(tripsList.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeProjects(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(projectsList.render(emp));
        }
    }

    @Transactional(readOnly = true)
    public static Result create(long departmentId) {
        Form<Employee> employeeForm = form(Employee.class);
        return ok(
                createPage.render(employeeForm, departmentId)
        );
    }

    @Transactional
    public static Result save(long departmentId) {
        Form<Employee> employeeForm = form(Employee.class).bindFromRequest();
        if (employeeForm.hasErrors()) {
            return badRequest(
                    createPage.render(employeeForm, departmentId)
            );
        }
        Employee employee = employeeForm.get();
        Department department = Department.findById(Department.class, departmentId);
        employee.setDepartment(department);
        employee.save();
        return goHome(departmentId);
    }

    @Transactional(readOnly=true)
    public static Result edit(long departmentId, long employeeId) {
        Employee employee = BaseEntity.findById(Employee.class, employeeId);
        if (employee == null) {
            return badRequest();
        } else {
            Form<Employee> employeeForm = form(Employee.class).fill(employee);
            return ok(
                    editPage.render(employeeForm, departmentId, employeeId)
            );
        }
    }

    @Transactional
    public static Result update(long departmentId, long employeeId) {
        Form<Employee> employeeForm = form(Employee.class).bindFromRequest();
        if (employeeForm.hasErrors()) {
            return badRequest(
                    editPage.render(employeeForm, departmentId, employeeId)
            );
        }
        Employee employee = employeeForm.get();
        Department department = Department.findById(Department.class, departmentId);
        employee.setDepartment(department);
        employee.setId(employeeId);
        employee.update();
        return redirect(
                routes.EmployeeController.employeeInfo(employeeId)
        );
    }

    @Transactional
    public static Result delete(long departmentId, long employeeId) {
        Employee employee = BaseEntity.findById(Employee.class, employeeId);
        if (employee != null) {
            employee.terminate();
        }
        return goHome(departmentId);
    }
}
