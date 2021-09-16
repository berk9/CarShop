package com.tusofia.project.carshop.database.entity.car;

public enum CategoryType {

    SEDAN( QuizConstants.SEDAN_TYPE_MESSAGE,"Sedans"),
    HATCHBACK(QuizConstants.HATCHBACK_TYPE_MESSAGE,"Hatchbacks"),
    STATION(QuizConstants.STATION_TYPE_MESSAGE,"Station Wagons"),
    SUV( QuizConstants.SUV_TYPE_MESSAGE,"SUVs and Crossovers");

    private final String displayValue;

    private final String categoryName;

    CategoryType(String displayValue, String categoryName) {
        this.displayValue = displayValue;
        this.categoryName = categoryName;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
