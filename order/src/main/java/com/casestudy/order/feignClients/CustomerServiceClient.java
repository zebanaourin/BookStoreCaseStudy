package com.casestudy.order.feignClients;


import com.casestudy.order.dto.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable("id") Long id);
}
