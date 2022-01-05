package com.example.onlineStore.customer;

import com.example.onlineStore.OnlineStoreApplication;
import com.example.onlineStore.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OnlineStoreApplication.class)
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerRepositoryDAO customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    void getAllCustomers() {
        customerRepository.save(this.createDummyCustomer());
        List<Customer> customers = customerRepository.findAll();
        assertEquals(customers, customerService.getAllCustomers());
    }

    @Test
    void addCustomer() {
        Customer dummyCustomer = this.createDummyCustomer();
        customerService.addCustomer(dummyCustomer);
        Optional<Customer> customer = customerRepository.findCustomerByUsername(dummyCustomer.getUsername());
        if (customer.isPresent()) {
            assertEquals(customer.get(), dummyCustomer);
        } else {
            assertEquals(null, dummyCustomer);
        }
    }

    @Test
    void deleteCustomer() {
        Customer dummyCustomer = this.createDummyCustomer();
        customerRepository.save(dummyCustomer);
        Optional<Customer> customer = customerRepository.findCustomerByUsername(dummyCustomer.getUsername());
        if (customer.isPresent()) {
            customerService.deleteCustomer(customer.get().getId());
            List<Customer> result = new ArrayList<>();
            customerRepository.findAll().forEach(e -> result.add(e));
            assertEquals(result.size(), 2); //only the initial 2 objects inserted are present
        }
    }

    @Test
    void updateCustomer() {
        Customer dummyCustomer = this.createDummyCustomer();
        customerRepository.save(dummyCustomer);
        Optional<Customer> customer = customerRepository.findCustomerByUsername(dummyCustomer.getUsername());
        if (customer.isPresent()) {
            customerService.updateCustomer(customer.get().getId(), null, null, null, "vasile2Updated@yahoo.com", null);
            Optional<Customer> updatedCustomer = customerRepository.findById(customer.get().getId());
            if (updatedCustomer.isPresent()) {
                assertEquals(updatedCustomer.get().getEmail(), "vasile2Updated@yahoo.com");
            }
        }
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