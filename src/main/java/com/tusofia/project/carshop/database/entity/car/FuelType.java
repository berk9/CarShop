package com.tusofia.project.carshop.database.entity.car;

public enum FuelType {
    PETROL("petrol"),
    DIESEL("diesel"),
    ELECTRIC("electric"),
    HYBRID("hybrid"),
    NOT_INTERESTED("not interested");

    private final String displayValue;

    FuelType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
