package controllers;

import models.dao.Employee;
import models.entity.EmployeeEntity;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    @Transactional(readOnly=true)
    public static Result employeeInfo(long id) {
        EmployeeEntity emp = EmployeeEntity.findById(id);
        if (emp == null) {
            return notFound();
        } else {
            return ok(employee.render(new Employee(emp)));
        }
    }

}
