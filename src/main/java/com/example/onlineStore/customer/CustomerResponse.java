package com.example.onlineStore.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CustomerResponse {
    private String customerName;
    private String ProductName;
    private String price;

    public CustomerResponse(String customerName, String productName) {
        this.customerName = customerName;
        ProductName = productName;
    }
}
