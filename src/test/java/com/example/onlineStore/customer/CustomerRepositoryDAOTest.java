package com.example.onlineStore.customer;

import com.example.onlineStore.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryDAOTest {

    @Autowired
    private CustomerRepositoryDAO underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    @DisplayName("Find Customer By Username")
    void findCustomerByUsername() {
        //given
        Customer customer = this.createDummyCustomer();
        underTest.saveAndFlush(customer);

        //when
        Optional<Customer> customerFoundByUsername = underTest.findCustomerByUsername(customer.getUsername());

        //then
        assertThat(customerFoundByUsername.isPresent()).isTrue();
    }

    @Test
    void findCustomerByEmail() {
        //given
        Customer customer = this.createDummyCustomer();
        underTest.saveAndFlush(customer);

        //when
        Optional<Customer> customerFoundByEmail = underTest.findCustomerByEmail(customer.getEmail());

        //then
        assertThat(customerFoundByEmail.isPresent()).isTrue();
    }

    @Test
    @Disabled
    void getJoinInfo() {
    }

    private Customer createDummyCustomer() {
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
        List<Product> userProductList = new ArrayList<>();
        userProductList.add(product1);
        userProductList.add(product2);
        userProductList.add(product3);

        return new Customer(
                "vasile2",
                "Vasile123!2",
                "vasile2@yahoo.com",
                "Vasile2",
                "Ion2",
                "Strada Vadului2, 75, Bucuresti",
                "0751515155",
                userProductList
        );
    }
}