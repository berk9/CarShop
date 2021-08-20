package com.tusofia.project.carshop.exception;

import com.tusofia.project.carshop.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Offer not found!")
public class PromotionNotFoundException extends NotFoundException {

    public PromotionNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
