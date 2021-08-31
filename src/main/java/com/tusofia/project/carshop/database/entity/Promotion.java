package com.tusofia.project.carshop.database.entity;


import com.tusofia.project.carshop.database.entity.car.Car;
import com.tusofia.project.carshop.database.entity.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
public class Promotion extends BaseEntity {

    @Column(name = "old_price", nullable = false)
    private BigDecimal oldPrice;

    @Column(name = "promo_price", nullable = false)
    private BigDecimal promoPrice;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @ManyToMany(targetEntity = Car.class ,fetch = FetchType.LAZY)
    @JoinTable(
            name = "promotion_cars",
            joinColumns = @JoinColumn(
                    name = "promotion_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "car_id",
                    referencedColumnName = "id"
            )
    )
    private List<Car> cars;


    public Promotion() {
    }

    public BigDecimal getOldPrice() { return oldPrice; }

    public void setOldPrice(BigDecimal oldPrice) { this.oldPrice = oldPrice; }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public LocalDateTime getValidUntil() { return validUntil; }

    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
