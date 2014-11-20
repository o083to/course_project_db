package models.dao;

import models.entity.EmployeeEntity;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.Date;

/**
 * Created by anna on 18.11.14.
 */
public class Employee {

    private static final PeriodFormatter PERIOD_FORMATTER = new PeriodFormatterBuilder()
            .appendPrefix("Лет: ")
            .appendYears()
            .appendPrefix(" Месяцев: ")
            .appendMonths()
            .appendPrefix(" Дней: ")
            .appendDays()
            .toFormatter();

    private String experienceStr;

    private Department department;

    private static final String SQL_GET_MANAGER_ID =
            "select\n" +
                "m.employee_id\n" +
            "from\n" +
                "employees e\n" +
                ",employees m\n" +
                ",departments d\n" +
            "where\n" +
                "e.employee_id = :employee_id\n" +
                "and d.department_id = e.department_id\n" +
                "and m.employee_id = d.manager_id";

    private final EmployeeEntity entity;

    public Employee(EmployeeEntity entity) {
        this.entity = entity;
    }

    public String getLastName() {
        return entity.getLastName();
    }

    public String getFirstName() {
        return entity.getFirstName();
    }

    public String getMiddleName() {
        String mName = entity.getMiddleName();
        return (mName == null) ? "" : mName;
    }

    public String getPosition() {
        return entity.getPosition();
    }

    public Date getHireDate() {
        return entity.getHireDate();
    }

    public String getExperienceStr() {
        if (experienceStr == null) {
            experienceStr = PERIOD_FORMATTER.print(new Period(
                    new LocalDate(entity.getHireDate())
                    ,new LocalDate()
                    ,PeriodType.yearMonthDay()
            ));
        }
        return experienceStr;
    }

    public Employee getManager() {
        Query query = JPA.em().createNativeQuery(SQL_GET_MANAGER_ID, EmployeeEntity.class);
        query.setParameter("employee_id", entity.getId());
        return new Employee((EmployeeEntity)query.getSingleResult());
    }

    public Department getDepartment() {
        if (department == null) {
            department = new Department(entity.getDepartment());
        }
        return department;
    }
}
