package models;

import play.db.jpa.JPA;

/**
 * Created by anna on 23.11.14.
 */
public abstract class BaseEntity {

    private static final String FORMAT_FOR_LIKE = "%%%1$s%%";

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public static <E> E findById(Class<E> entityClass, long id) {
        return JPA.em().find(entityClass, id);
    }

    public void update() {
        JPA.em().merge(this);
    }

    protected static String formatForLike(String arg) {
        return String.format(FORMAT_FOR_LIKE, arg);
    }
}
