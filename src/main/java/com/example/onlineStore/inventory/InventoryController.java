package com.example.onlineStore.inventory;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "onlineStore/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllProducts(){
        return inventoryService.getAllProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody Inventory product) {
        inventoryService.addProduct(product);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long productId,
                               @RequestParam(required = false) String productName,
                               @RequestParam(required = false) Integer quantity,
                               @RequestParam(required = false) Float price) {
        inventoryService.updateProduct(productId, productName, quantity, price);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        inventoryService.deleteProduct(productId);
    }
}
