package com.tusofia.project.carshop.exception;

import com.tusofia.project.carshop.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found!")
public class CarNotFoundException extends NotFoundException {

    public CarNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }
}