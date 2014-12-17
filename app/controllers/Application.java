package controllers;

import models.User;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import utils.CommonUtils;
import views.html.index;
import views.html.lol;
import views.html.security.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	@Security.Authenticated(Authorization.class)
	public static Result help() {
		return ok(
				lol.render()
		);
	}

	@Security.Authenticated(Authorization.class)
	public static Result signup() {
		return ok(signup.render(form(SignUpForm.class)));
	}

	@Security.Authenticated(Authorization.class)
	public static Result register() {
		Form<SignUpForm> signUpForm = form(SignUpForm.class).bindFromRequest();
		if (signUpForm.hasErrors()) {
			return badRequest(signup.render(signUpForm));
		} else {
			session().clear();
            session("login", signUpForm.get().login);
            return redirect(
                routes.Application.index()
            );
		}
	}
    
    public static Result login() {
    	return ok(login.render(form(LoginForm.class)));
    }
    
    public static Result logout() {
    	session().clear();
    	flash("success", "Вы вышли из системы");
        return redirect(
            routes.Application.login()
        );
    }
    
    public static Result authenticate() {
    	Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
    	if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("login", loginForm.get().login);
            return redirect(
                routes.DepartmentsController.departmentsList()
            );
        }
    }
    
    public static class LoginForm {
    	public String login;
    	public String password;
    	
    	public String validate() {
    		if (User.authenticate(login, password) == null) {
    			return "Неверный логин или пароль!";
    		}
    		return null;
    	}
    }
    
    public static class SignUpForm {
    	public String login;
    	public String password1;
    	public String password2;
    	
    	public String validate() {
    		if (CommonUtils.stringIsEmpty(login) || CommonUtils.stringIsEmpty(password1) || CommonUtils.stringIsEmpty(password2)) {
    			return "Не все поля заполнены!";
    		} else if (!password1.equals(password2)) {
    			return "Пароли не совпадают!";
    		} else if (!User.add(login, password1)) {
    			return "Пользователь с таким именем уже зарегистрирован!";
    		} else return null;
    	}
    }

}
