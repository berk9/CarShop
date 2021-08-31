package com.tusofia.project.carshop.dto.view;

import com.tusofia.project.carshop.dto.binding.auth.UserServiceModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsViewModel {

    private Long id;
    private BigDecimal totalPrice;
    private UserServiceModel customer;
    private LocalDateTime createdOn;
    private Boolean approved; //thymeleaf does not recognize it if it's isApproved
    private Boolean successful; //thymeleaf does not recognize it if it's isSuccessful
    private LocalDate waitingTime;
    private String comment;
    private List<CarDetailsViewModel> cars;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public LocalDate getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(LocalDate waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<CarDetailsViewModel> getCars() {
        return cars;
    }

    public void setCars(List<CarDetailsViewModel> cars) {
        this.cars = cars;
    }

    public Boolean getApproved() { return approved; }

    public void setApproved(Boolean approved) { this.approved = approved; }
}
