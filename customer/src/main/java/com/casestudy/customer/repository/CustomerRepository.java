package com.casestudy.customer.repository;

import com.casestudy.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
