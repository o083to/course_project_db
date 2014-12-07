package models;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Заказчик
 * Created by anna on 08.11.14.
 */

@Entity
@Table(name = "customers")
@SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @Column(name = "customer_id")
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    @Constraints.Required
    private String name;

    @Column(name = "location", length = 200, nullable = false)
    @Constraints.Required
    private String location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Project> projects;

    private static final String SQL_GET_CUSTOMERS = "select * from customers where upper(name) like upper(:name) order by name";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static List<Customer> getList(String name) {
        return JPA.em().createNativeQuery(SQL_GET_CUSTOMERS, Customer.class)
                .setParameter("name", formatForLike(name))
                .getResultList();
    }

    public static Map<String, String> getAllCustomersAsOptions() {
        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        List<Customer> customers = JPA.em().createNativeQuery(SQL_GET_CUSTOMERS, Customer.class)
                .setParameter("name", formatForLike(""))
                .getResultList();
        for (Customer customer : customers) {
            options.put(String.valueOf(customer.getId()), customer.getName());
        }
        return options;
    }
}
