package controllers;

import models.BaseEntity;
import models.Employee;
import models.Trip;
import play.mvc.Controller;

import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;
import views.html.trip.*;

import static play.data.Form.form;

/**
 * Created by anna on 30.11.14.
 */
public class TripsController extends Controller {

    public static Result goHome(long employeeId) {
        return redirect(routes.EmployeeController.employeeTrips(employeeId));
    }

    @Transactional(readOnly = true)
    public static Result create(long employeeId) {
        Form<Trip> tripForm = form(Trip.class);
        return ok(
                createPage.render(tripForm, employeeId)
        );
    }

    @Transactional
    public static Result save(long employeeId) {
        Form<Trip> tripForm = form(Trip.class).bindFromRequest();
        if (tripForm.hasErrors()) {
            return badRequest(
                    createPage.render(tripForm, employeeId)
            );
        }
        Trip trip = tripForm.get();
        Employee employee = Employee.findById(Employee.class, employeeId);
        trip.setEmployee(employee);
        trip.save();
        return goHome(employeeId);
    }

    @Transactional
    public static Result delete(long employeeId, long tripId) {
        Trip trip = BaseEntity.findById(Trip.class, tripId);
        if (trip != null) {
            trip.delete();
        }
        return goHome(employeeId);
    }

    @Transactional(readOnly=true)
    public static Result edit(long employeeId, long tripId) {
        Trip trip = BaseEntity.findById(Trip.class, tripId);
        if (trip == null) {
            return badRequest();
        } else {
            Form<Trip> tripForm = form(Trip.class).fill(trip);
            return ok(
                    editPage.render(tripForm, employeeId, tripId)
            );
        }
    }

    @Transactional
    public static Result update(long employeeId, long tripId) {
        Form<Trip> tripForm = form(Trip.class).bindFromRequest();
        if (tripForm.hasErrors()) {
            return badRequest(
                    editPage.render(tripForm, employeeId, tripId)
            );
        }
        Trip trip = tripForm.get();
        Employee employee = Employee.findById(Employee.class, employeeId);
        trip.setEmployee(employee);
        trip.setId(tripId);
        trip.update();
        return goHome(employeeId);
    }
}
