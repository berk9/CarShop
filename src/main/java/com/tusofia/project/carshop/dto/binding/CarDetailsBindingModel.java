package com.tusofia.project.carshop.dto.binding;

import com.tusofia.project.carshop.database.entity.car.Brand;
import com.tusofia.project.carshop.database.entity.car.CarType;
import com.tusofia.project.carshop.database.entity.car.FuelType;

import javax.validation.constraints.NotNull;

public class CarDetailsBindingModel {

    private Long carId;

    @NotNull(message = "cannot be null")
    private Brand brand;

    @NotNull(message = "cannot be null")
    private CarType carType;

    @NotNull(message = "cannot be null")
    private FuelType fuelType;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

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
