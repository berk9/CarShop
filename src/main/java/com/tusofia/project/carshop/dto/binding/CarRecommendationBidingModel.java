package com.tusofia.project.carshop.dto.binding;

public class CarRecommendationBidingModel {

    public CarRecommendationBidingModel() {
    }

    private Long id;

    private CategoryBindingModel category;

    private CarDetailsBindingModel carDetailsBindingModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryBindingModel getCategory() {
        return category;
    }

    public void setCategory(CategoryBindingModel category) {
        this.category = category;
    }

    public CarDetailsBindingModel getCarDetailsBindingModel() {
        return carDetailsBindingModel;
    }

    public void setCarDetailsBindingModel(CarDetailsBindingModel carDetailsBindingModel) {
        this.carDetailsBindingModel = carDetailsBindingModel;
    }
}
