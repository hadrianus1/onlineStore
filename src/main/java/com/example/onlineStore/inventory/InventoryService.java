package com.example.onlineStore.inventory;

import com.example.onlineStore.exception.BadRequestException;
import com.example.onlineStore.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepositoryDAO inventoryRepository;

    public List<Inventory> getAllProducts() {
        return inventoryRepository.findAll();
    }

    public void addProduct(Inventory product) {
        Optional<Inventory> productByName = inventoryRepository.findProductByProductName(product.getProductName());
        if (productByName.isPresent()) {
            throw new BadRequestException("Product " + product.getProductName() + " already exists!");
        }
        inventoryRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, String productName, Integer quantity, Float price) {
        Inventory product = inventoryRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product with the id " + productId + "does not exist!"));

        if (productName != null && productName.length() > 0 && !Objects.equals(product.getProductName(), productName)) {
            Optional<Inventory> inventoryOptional = inventoryRepository.findProductByProductName(productName);
            if (inventoryOptional.isPresent()) {
                throw new BadRequestException("Product " + productName + " already exists!");
            }
            product.setProductName(productName);
        }

        if (quantity != null && !Objects.equals(product.getQuantity(), quantity)) {
            product.setQuantity(quantity);
        }

        if (price != null && price > 0 && !Objects.equals(product.getPrice(), price)) {
            product.setPrice(price);
        }
    }

    public void deleteProduct(Long productId) {
        if (!inventoryRepository.existsById(productId)) {
            throw new ObjectNotFoundException("Product with the id " + productId + "does not exist!");
        }
        inventoryRepository.deleteById(productId);
    }
}
