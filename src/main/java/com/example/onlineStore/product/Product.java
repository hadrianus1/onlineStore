package com.example.onlineStore.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Column(
            name = "productId"
    )
    private Long productId;

    @Column(
            name = "productName"
    )
    private String productName;

    @Column(
            name = "quantity"
    )
    private Integer quantity;

    @Column(
            name = "price"
    )
    private Float price;

    public Product(String productName, Integer quantity, Float price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
