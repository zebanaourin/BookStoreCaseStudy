package com.casestudy.book.exceptions;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException(@NotNull @Size(min = 1, max = 255) String s) {
        super(s);
    }
}
