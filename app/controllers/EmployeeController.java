package controllers;

import models.Employee;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;
import views.html.employee.*;

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

}
