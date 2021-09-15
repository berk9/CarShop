package com.tusofia.project.carshop.database.entity.car;

public enum Brand {
    MERCEDES("Mercedes"),
    BMW("BMW"),
    AUDI("Audi"),
    VW("Volkswagen"),
    OTHER("Other");

    private final String displayValue;

    Brand(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
