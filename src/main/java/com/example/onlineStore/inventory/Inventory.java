package com.example.onlineStore.inventory;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "inventory",
        uniqueConstraints = {
                @UniqueConstraint(name = "productName_unique", columnNames = "productName")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Inventory {
    @Id
    @SequenceGenerator(
            name = "inventory_sequence",
            sequenceName = "inventory_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_sequence"
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

    public Inventory(String productName, Integer quantity, Float price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
