package com.tusofia.project.carshop.database.entity.car;

import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "car_details")
public class CarDetails extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Column(name = "engine_type", nullable = false)
    private EngineType engineType;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
}

