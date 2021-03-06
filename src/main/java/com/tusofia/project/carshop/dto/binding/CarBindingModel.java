package com.tusofia.project.carshop.dto.binding;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CarBindingModel {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3,max = 32, message = "Name must be more than 3 and less than 32 characters")
    private String name;

    @NotEmpty(message = "Description cannot be blank")
    @Size(min = 10, max = 1024, message = "Description length must be more than 10 and less than 1024 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal price;

    @NotNull
    private Boolean activity;

    @NotEmpty(message = "Image source cannot be blank")
    private String imgSrc;

    //TODO: Make it categoryDTO class.
    @NotNull(message = "Category cannot be null")
    private CategoryBindingModel category;

    @Valid
    private CarDetailsAddBindingModel carDetailsBindingModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public CategoryBindingModel getCategory() { return category; }

    public void setCategory(CategoryBindingModel category) { this.category = category; }

    public CarDetailsAddBindingModel getCarDetailsBindingModel() {
        return carDetailsBindingModel;
    }

    public void setCarDetailsBindingModel(CarDetailsAddBindingModel carDetailsBindingModel) {
        this.carDetailsBindingModel = carDetailsBindingModel;
    }
}
