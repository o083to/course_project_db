package models.dao;

import models.entity.DepartmentEntity;

/**
 * Created by anna on 20.11.14.
 */
public class Department {

    private final DepartmentEntity entity;

    public Department(DepartmentEntity entity) {
        this.entity = entity;
    }

    public String getName() {
        return entity.getName();
    }
}
