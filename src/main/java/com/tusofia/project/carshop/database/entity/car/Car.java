package com.tusofia.project.carshop.database.entity.car;


import com.tusofia.project.carshop.database.entity.Category;
import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_source", nullable = false, unique = true)
    private String imgSrc;

    @Column(name = "is_active", nullable = false)
    private Boolean activity;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToOne(targetEntity = CarDetails.class)
    private CarDetails carDetails;

    @PreRemove
    private void removeCarFromCategory(){
        this.category.getCars().remove(this);
    }

    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Boolean getActivity() { return activity; }

    public void setActivity(Boolean activity) { this.activity = activity; }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
