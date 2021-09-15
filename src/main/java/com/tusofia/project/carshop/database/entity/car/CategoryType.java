package com.tusofia.project.carshop.database.entity.car;

public enum CategoryType {

    SEDAN("Sedans", "sedan"),
    SUV("suv", "SUVs and Crossovers"),
    HATCHBACK("hatchback", "Hatchbacks"),
    STATION("station", "Station Wagons");

    private final String[] displayValue;

    private final String categoryName;

    CategoryType(String categoryName, String... displayValue) {
        this.displayValue = displayValue;
        this.categoryName = categoryName;
    }

    public String[] getDisplayValue() {
        return displayValue;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
