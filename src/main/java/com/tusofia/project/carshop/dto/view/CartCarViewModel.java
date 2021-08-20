package com.tusofia.project.carshop.dto.view;

public class CartCarViewModel {

    private CarDetailsViewModel carDetailsViewModel;
    private int quantity;

    public CartCarViewModel() {
    }

    public CarDetailsViewModel getCarDetailsViewModel() {
        return carDetailsViewModel;
    }

    public void setCarDetailsViewModel(CarDetailsViewModel carDetailsViewModel) {
        this.carDetailsViewModel = carDetailsViewModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
