package models;

import play.db.jpa.JPA;

/**
 * Created by anna on 23.11.14.
 */
public abstract class BaseEntity {

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public static Object findById(Class entityClass, long id) {
        return JPA.em().find(entityClass, id);
    }

    public void update() {
        JPA.em().merge(this);
    }
}
