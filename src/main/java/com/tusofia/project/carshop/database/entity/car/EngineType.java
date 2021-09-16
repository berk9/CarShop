package com.tusofia.project.carshop.database.entity.car;

public enum EngineType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    ELECTRIC("Electric"),
    HYBRID("Hybrid"),
    NOT_INTERESTED("Not interested");

    private final String displayValue;

    EngineType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
