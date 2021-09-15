package com.tusofia.project.carshop.database.entity.car;

public enum CarType {
    SPORT(QuizConstants.SPORT_CATEGORY_MESSAGE),
    ADVENTURE(QuizConstants.ADVENTURE_CATEGORY_MESSAGE),
    FAMILY(QuizConstants.FAMILY_CATEGORY_MESSAGE),
    SAFETY(QuizConstants.SAFETY_CATEGORY_MESSAGE);

    private final String displayValue;

    CarType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
