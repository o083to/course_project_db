package models;

import javax.persistence.*;
import java.util.List;

/**
 * Заказчик
 * Created by anna on 08.11.14.
 */

@Entity
@Table(name = "customers")
@SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @Column(name = "customer_id")
    private long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Project> projects;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
