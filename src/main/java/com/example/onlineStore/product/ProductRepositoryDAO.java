package com.example.onlineStore.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepositoryDAO extends JpaRepository<Product, Long> {
    Optional<Product> findProductByProductName(String productName);
}
