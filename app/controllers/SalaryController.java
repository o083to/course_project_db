package controllers;

import models.BaseEntity;
import models.Employee;
import models.Salary;
import play.mvc.Controller;

import play.db.jpa.Transactional;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import views.html.salary.*;

import java.util.Calendar;

/**
 * Created by anna on 23.11.14.
 */

@Security.Authenticated(Authorization.class)
public class SalaryController extends Controller {

    public static Result goHome(long employeeId) {
        return redirect(routes.EmployeeController.employeeSalaries(employeeId));
    }

    @Transactional
    public static Result save(long employeeId) {
        Form<Salary> salaryForm = form(Salary.class).bindFromRequest();
        if(salaryForm.hasErrors()) {
            return badRequest(createPage.render(salaryForm, employeeId));
        }
        Salary salary = salaryForm.get();
        Employee emp = Employee.findById(Employee.class, employeeId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(salary.getStartDate());
        calendar.add(Calendar.DATE, -1);
        Salary.setLastEndDate(calendar.getTime(), employeeId);

        salary.setEmployee(emp);
        salary.save();
        return goHome(employeeId);
    }

    @Transactional(readOnly=true)
    public static Result create(long employeeId) {
        Form<Salary> salaryForm = form(Salary.class);
        return ok(
                createPage.render(salaryForm, employeeId)
        );
    }

    @Transactional
    public static Result delete(long employeeId, long salaryId) {
        Salary salary = BaseEntity.findById(Salary.class, salaryId);
        if (salary != null) {
            salary.delete();
            Salary.deleteLastEndDate(employeeId);
        }
        return goHome(employeeId);
    }

}
