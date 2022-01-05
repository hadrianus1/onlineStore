package com.example.onlineStore.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepositoryDAO extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findProductByProductName(String productName);
}
