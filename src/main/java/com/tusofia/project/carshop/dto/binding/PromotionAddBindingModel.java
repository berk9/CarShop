package com.tusofia.project.carshop.dto.binding;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PromotionAddBindingModel {

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal promoPrice;

    @NotBlank(message = "Date cannot be blank")
    private String validUntil;

    @NotEmpty(message = "There must be more than zero cars")
    private String[] cars; // It is string[] because multiple select option in html works with it.

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String[] getCars() {
        return cars;
    }

    public void setCars(String[] cars) {
        this.cars = cars;
    }
}
