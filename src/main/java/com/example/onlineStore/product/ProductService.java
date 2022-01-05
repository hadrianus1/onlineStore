package com.example.onlineStore.product;

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
public class ProductService {
    private final ProductRepositoryDAO productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        Optional<Product> productByName = productRepository.findProductByProductName(product.getProductName());
        if (productByName.isPresent()) {
            throw new BadRequestException("Product " + product.getProductName() + " already exists!");
        }
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, String productName, Integer quantity, Float price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product with the id " + productId + "does not exist!"));

        if (productName != null && productName.length() > 0 && !Objects.equals(product.getProductName(), productName)) {
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
        if (!productRepository.existsById(productId)) {
            throw new ObjectNotFoundException("Product with the id " + productId + "does not exist!");
        }
        productRepository.deleteById(productId);
    }
}
