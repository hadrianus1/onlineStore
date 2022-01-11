package com.example.onlineStore.customer;

import com.example.onlineStore.OnlineStoreApplication;
import com.example.onlineStore.exception.BadRequestException;
import com.example.onlineStore.inventory.Inventory;
import com.example.onlineStore.inventory.InventoryRepositoryDAO;
import com.example.onlineStore.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = OnlineStoreApplication.class)
@Transactional
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryDAO customerRepository;
    @Mock
    private InventoryRepositoryDAO inventoryRepository;
    private AutoCloseable autoCloseable;

    @Autowired
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerRepository, inventoryRepository);
        this.createDummyInventory();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {
        //given
        customerRepository.save(this.createDummyCustomer());
        //when
        List<Customer> allCustomers = underTest.getAllCustomers();
        //then
        List<Customer> findAll = customerRepository.findAll();
        assertThat(findAll).isEqualTo(allCustomers);
    }

    @Test
    void addNewCustomer() {
        //given
        Customer dummyCustomer = this.createDummyCustomer();
        //when
        underTest.addCustomer(dummyCustomer);
        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer).isEqualTo(dummyCustomer);
    }

    @Test
    void addAlreadyExistingCustomer() {
        //given
        Customer dummyCustomer = this.createDummyCustomer();
        Optional<Customer> optionalCustomer = Optional.of(dummyCustomer);
        given(customerRepository.findCustomerByUsername(dummyCustomer.getUsername())).willReturn(optionalCustomer);

        //when
        //then
        assertThatThrownBy(() -> underTest.addCustomer(dummyCustomer))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Username " + dummyCustomer.getUsername() + " already exists!");

        verify(customerRepository, never()).save(any());
    }

    @Test
    void deleteCustomer() {
        Customer dummyCustomer = this.createDummyCustomer();
        customerRepository.save(dummyCustomer);
        Optional<Customer> customer = customerRepository.findCustomerByUsername(dummyCustomer.getUsername());
        if (customer.isPresent()) {
            underTest.deleteCustomer(customer.get().getId());
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
            underTest.updateCustomer(customer.get().getId(), null, null, null, "vasile2Updated@yahoo.com", null);
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

    private void createDummyInventory(){
        Inventory inventory1 = new Inventory(
                "laptop",
                20,
                6000.0f);
        Inventory inventory2 = new Inventory(
                "tv",
                25,
                4000.50f);
        Inventory inventory3 = new Inventory(
                "mouse",
                30,
                99f);
        Inventory inventory4 = new Inventory(
                "clothes",
                200,
                2000.75f);
        this.inventoryRepository.saveAll(new ArrayList<Inventory>(Arrays.asList(inventory1, inventory2, inventory3, inventory4)));
    }
}