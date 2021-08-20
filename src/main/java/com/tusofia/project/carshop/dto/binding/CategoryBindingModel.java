package com.tusofia.project.carshop.dto.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class CategoryBindingModel {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3,max = 32, message = "Name must be less than 32 characters")
    private String name;

    @NotEmpty(message = "There must be more than zero products")
    private List<CarBindingModel> cars;

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

    public List<CarBindingModel> getCars() { return cars; }

    public void setCars(List<CarBindingModel> cars) { this.cars = cars; }
}
