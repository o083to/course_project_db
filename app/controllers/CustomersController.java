package controllers;

import models.BaseEntity;
import models.Customer;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.customer.*;

import static play.data.Form.form;

/**
 * Created by anna on 07.12.14.
 */
public class CustomersController extends Controller {

    public static Result goHome() {
        return redirect(routes.CustomersController.customersList(""));
    }

    @Transactional(readOnly = true)
    public static Result customersList(String name) {
        return ok(
                list.render(name)
        );
    }

    @Transactional(readOnly = true)
    public static Result create() {
        Form<Customer> customerForm = form(Customer.class);
        return ok(
                createPage.render(customerForm)
        );
    }

    @Transactional
    public static Result save() {
        Form<Customer> customerForm = form(Customer.class).bindFromRequest();
        if (customerForm.hasErrors()) {
            return badRequest(
                    createPage.render(customerForm)
            );
        }
        customerForm.get().save();
        return goHome();
    }

    @Transactional
    public static Result delete(long customerId) {
        Customer customer = BaseEntity.findById(Customer.class, customerId);
        if (customer != null) {
            customer.delete();
        }
        return goHome();
    }

    @Transactional(readOnly = true)
    public static Result edit(long customerId) {
        Customer customer = BaseEntity.findById(Customer.class, customerId);
        if (customer == null) {
            return badRequest();
        } else {
            Form<Customer> customerForm = form(Customer.class).fill(customer);
            return ok(
                    editPage.render(customerForm, customerId)
            );
        }
    }

    @Transactional
    public static Result update(long customerId) {
        Form<Customer> customerForm = form(Customer.class).bindFromRequest();
        if (customerForm.hasErrors()) {
            return badRequest(
                    editPage.render(customerForm, customerId)
            );
        }
        Customer customer = customerForm.get();
        customer.setId(customerId);
        customer.update();
        return goHome();
    }
}
