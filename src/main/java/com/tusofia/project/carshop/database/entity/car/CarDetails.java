package com.tusofia.project.carshop.database.entity.car;

import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "car_details")
@Entity
public class CarDetails extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}

