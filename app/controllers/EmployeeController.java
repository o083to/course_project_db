package controllers;

import models.Employee;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

public class EmployeeController extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @Transactional(readOnly=true)
    public static Result employeeInfo(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeSalaries(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee_salary.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeVacations(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee_vacation.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeTrips(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee_trips.render(emp));
        }
    }

    @Transactional(readOnly=true)
    public static Result employeeProjects(long id) {
        Employee emp = Employee.findById(Employee.class, id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee_projects.render(emp));
        }
    }

}
