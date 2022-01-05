package com.example.onlineStore.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepositoryDAO extends JpaRepository<Customer, Long> {
    //@Query("SELECT c from Customer c where c.username = ?1")
    Optional<Customer> findCustomerByUsername(String username);

    Optional<Customer> findCustomerByEmail(String email);

    @Query(value = "SELECT new com.example.onlineStore.customer.CustomerResponse(c.username, p.productName) FROM Customer c JOIN c.productList p")
    public List<CustomerResponse> getJoinInfo();

}
