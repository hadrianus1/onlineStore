package com.example.onlineStore.customer;

import com.example.onlineStore.product.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner customerCommandLineRunner(CustomerRepositoryDAO customerRepository) {
        return args -> {
            Product product1 = new Product(
                    "laptop",
                    1,
                    6000.0f);
            Product product2 = new Product(
                    "tv",
                    1,
                    4000.50f);
            Product product3 = new Product(
                    "mouse",
                    2,
                    99f);
            List<Product> vasileProductList = new ArrayList<>();
            vasileProductList.add(product1);
            vasileProductList.add(product2);
            vasileProductList.add(product3);
            Customer vasile = new Customer(
                    "vasile",
                    "Vasile123!",
                    "vasile@yahoo.com",
                    "Vasile",
                    "Ion",
                    "Strada Vadului, 75, Bucuresti",
                    "0751515151",
                    vasileProductList

            );

            Product product4 = new Product(
                    "laptop",
                    2,
                    6000.0f);
            Product product5 = new Product(
                    "clothes",
                    10,
                    2000.75f);

            List<Product> mariaProductList = new ArrayList<>();
            mariaProductList.add(product4);
            mariaProductList.add(product5);
            mariaProductList.add(product3);

            Customer maria = new Customer(
                    "maria",
                    "Maria123!",
                    "maria@yahoo.com",
                    "Maria",
                    "Ion",
                    "Strada Vadului, 76, Toronto",
                    "0751515153",
                    mariaProductList
            );
            customerRepository.saveAll(new ArrayList<Customer>(Arrays.asList(vasile, maria)));
        };
    }
}
