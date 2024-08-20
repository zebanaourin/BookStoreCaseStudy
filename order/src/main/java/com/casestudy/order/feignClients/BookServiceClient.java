package com.casestudy.order.feignClients;


import com.casestudy.order.dto.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("book-service")
public interface BookServiceClient {

    @GetMapping("/{id}")
    Book getBookById(@PathVariable Long id);
}
