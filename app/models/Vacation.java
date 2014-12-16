package models;

import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Отпуск
 * Created by anna on 09.11.14.
 */

@Entity
@Table(name = "vacations")
@SequenceGenerator(name = "vacations_seq", sequenceName = "vacations_seq")
public class Vacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacations_seq")
    @Column(name = "vacation_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "start_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Constraints.Required
    @Formats.DateTime(pattern="dd-MM-yy")
    private Date endDate;

    @Column(name = "reason", length = 250, nullable = false)
    @Constraints.Required
    private String reason;

    private static final Map<String, String> REASONS = new LinkedHashMap<String, String>(){{
        put("Ежегодный оплачиваемый отпуск", "Ежегодный оплачиваемый отпуск");
        put("Отпуск без сохранения заработной платы", "Отпуск без сохранения заработной платы");
        put("Дополнительный отпуск", "Дополнительный отпуск");
        put("Оплачиваемый учебный отпуск", "Оплачиваемый учебный отпуск");
        put("Неоплачиваемый учебный отпуск", "Неоплачиваемый учебный отпуск");
        put("Отпуск по беременности и родам", "Отпуск по беременности и родам");
        put("Отпуск по уходу за ребёнком", "Отпуск по уходу за ребёнком");
    }};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static Map<String, String> getReasonsAsOptions() {
        return REASONS;
    }
}
