package com.example.onlineStore.customer;

import com.example.onlineStore.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "onlineStore/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "getAllCommands")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping(path = "orderCommand")
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @PutMapping(path = "updateCommand/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String password,
                               @RequestParam(required = false) String phoneNumber,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) List<Product> productList) {
        customerService.updateCustomer(customerId, address, password, phoneNumber, email, productList);
    }

    @DeleteMapping(path = "deleteCommand/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping(path = "/getInfo")
    public List<CustomerResponse> getCustomerProductsNames(){
        return customerService.getCustomerProductsNames();
    }
}
