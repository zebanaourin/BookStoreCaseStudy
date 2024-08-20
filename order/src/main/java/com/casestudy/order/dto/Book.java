package com.casestudy.order.dto;


import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;

    public int getStock() {return stock;}
}
