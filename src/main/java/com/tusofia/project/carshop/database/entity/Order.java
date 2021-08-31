package com.tusofia.project.carshop.database.entity;


import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "is_successful")
    private Boolean successful; //thymeleaf does not recognize it if it's isSuccessful

    @Column(name = "waiting_time")
    private LocalDate waitingTime;

    @Column(name = "is_approved", nullable = false)
    private Boolean approved;

    @Column(name = "comment")
    private String comment;

    @Version
    private Integer version;

    @ManyToMany(targetEntity = Car.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_cars",
            joinColumns = @JoinColumn(
                    name = "order_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "car_id",
                    referencedColumnName = "id"
            )
    )
    private List<Car> cars;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User customer;

    public Order() {
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getSuccessful() { return successful; }

    public void setSuccessful(Boolean successful) { this.successful = successful; }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public User getCustomer() { return customer; }

    public void setCustomer(User customer) { this.customer = customer; }

    public Boolean getApproved() { return approved; }

    public void setApproved(Boolean approved) { this.approved = approved; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocalDate getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(LocalDate waitingTime) {
        this.waitingTime = waitingTime;
    }
}
