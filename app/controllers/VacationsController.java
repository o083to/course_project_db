package controllers;

import models.BaseEntity;
import models.Employee;
import models.Vacation;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;
import views.html.vacation.*;

import static play.data.Form.form;

/**
 * Created by anna on 30.11.14.
 */
public class VacationsController extends Controller {

    public static Result goHome(long employeeId) {
        return redirect(routes.EmployeeController.employeeVacations(employeeId));
    }

    @Transactional(readOnly=true)
    public static Result create(long employeeId) {
        Form<Vacation> vacationForm = form(Vacation.class);
        return ok(
                createPage.render(vacationForm, employeeId)
        );
    }

    @Transactional
    public static Result save(long employeeId) {
        Form<Vacation> vacationForm = form(Vacation.class).bindFromRequest();
        if (vacationForm.hasErrors()) {
            return badRequest(
                    createPage.render(vacationForm, employeeId)
            );
        }
        Vacation vacation = vacationForm.get();
        Employee employee = Employee.findById(Employee.class, employeeId);
        vacation.setEmployee(employee);
        vacation.save();
        return goHome(employeeId);
    }

    @Transactional
    public static Result delete(long employeeId, long vacationId) {
        Vacation vacation = BaseEntity.findById(Vacation.class, vacationId);
        if (vacation != null) {
            vacation.delete();
        }
        return goHome(employeeId);
    }

    @Transactional(readOnly=true)
    public static Result edit(long employeeId, long vacationId) {
        Vacation vacation = BaseEntity.findById(Vacation.class, vacationId);
        if (vacation == null) {
            return badRequest();
        } else {
            Form<Vacation> vacationForm = form(Vacation.class).fill(vacation);
            return ok(
                    editPage.render(vacationForm, employeeId, vacationId)
            );
        }
    }

    @Transactional
    public static Result update(long employeeId, long vacationId) {
        Form<Vacation> vacationForm = form(Vacation.class).bindFromRequest();
        if (vacationForm.hasErrors()) {
            return badRequest(
                    editPage.render(vacationForm, employeeId, vacationId)
            );
        }
        Vacation vacation = vacationForm.get();
        Employee employee = Employee.findById(Employee.class, employeeId);
        vacation.setEmployee(employee);
        vacation.setId(vacationId);
        vacation.update();
        return goHome(employeeId);
    }
}
