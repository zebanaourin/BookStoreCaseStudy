package com.casestudy.order.exception;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DuplicateOrderFoundException extends RuntimeException {
    public DuplicateOrderFoundException(@NotNull @Size(min = 1, max = 255) String s) {
        super(s);
    }
}