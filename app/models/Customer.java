package models;

import javax.persistence.*;

/**
 * Заказчик
 * Created by anna on 08.11.14.
 */

@Entity
@Table(name = "customers")
@SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq")
public class Customer {

    private long id;
    private String name;
    private String location;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @Column(name = "customer_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", length = 100, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "location", length = 100, nullable = false)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
