package com.tusofia.project.carshop.dto.binding;


import com.tusofia.project.carshop.dto.binding.auth.UserServiceModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderBindingModel {

    private Long id;

    @NotBlank(message = "Price cannot be blank")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal totalPrice;

    @Future(message = "Please select a time in the future")
    @DateTimeFormat(iso= DateTimeFormat.ISO.TIME)
    private LocalDateTime waitingTime;

    @NotNull(message = "Boolean should be either true or false")
    private Boolean successful; //thymeleaf does not recognize it if it's isSuccessful

    @NotEmpty(message = "There must be more than zero cars")
    private List<CarBindingModel> cars;

    @NotNull(message = "Customer cannot be null")
    private UserServiceModel customer;

    @NotNull(message = "Boolean should be either true or false")
    private Boolean approved;

    @Size(max = 200, message = "Comment length must be less than 200 characters")
    private String comment;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getWaitingTime() { return waitingTime; }

    public void setWaitingTime(LocalDateTime waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Boolean getSuccessful() { return successful; }

    public void setSuccessful(Boolean successful) { this.successful = successful; }

    public List<CarBindingModel> getCars() { return cars; }

    public void setCars(List<CarBindingModel> cars) { this.cars = cars; }

    public UserServiceModel getCustomer() { return customer; }

    public void setCustomer(UserServiceModel customer) { this.customer = customer; }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
