package com.tusofia.project.carshop.database.entity;


import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", targetEntity = Car.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Car> cars;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
