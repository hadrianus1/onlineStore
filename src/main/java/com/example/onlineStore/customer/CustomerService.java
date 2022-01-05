package com.example.onlineStore.customer;

import com.example.onlineStore.exception.BadRequestException;
import com.example.onlineStore.exception.ObjectNotFoundException;
import com.example.onlineStore.inventory.Inventory;
import com.example.onlineStore.inventory.InventoryRepositoryDAO;
import com.example.onlineStore.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepositoryDAO customerRepository;

    private final InventoryRepositoryDAO inventoryRepository;

    @Autowired
    public CustomerService(CustomerRepositoryDAO customerRepository, InventoryRepositoryDAO inventoryRepository) {
        this.customerRepository = customerRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<CustomerResponse> getCustomerProductsNames() {
        return customerRepository.getJoinInfo();
    }

    @Transactional
    public void addCustomer(Customer customer) {
        Optional<Customer> customerByUsername = customerRepository.findCustomerByUsername(customer.getUsername());
        if (customerByUsername.isPresent()) {
            throw new BadRequestException("Username " + customer.getUsername() + " already exists!");
        }
        customerRepository.save(customer);

        //update the global inventory quantity for each product bought
        List<Product> productList = customer.getProductList();
        updateInventory(productList, "substract");
    }

    public void deleteCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ObjectNotFoundException("Customer with the id " + customerId + "does not exist!");
        }
        Optional<Customer> customer = customerRepository.findById(customerId);
        customerRepository.deleteById(customerId);

        //update the global inventory quantity for each product bought
        List<Product> productList = customer.get().getProductList();
        updateInventory(productList, "add");
    }



    @Transactional
    public void updateCustomer(Long customerId, String address, String password, String phoneNumber, String email, List<Product> productList) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException("Customer with the id " + customerId + "does not exist!"));

        if (address != null && address.length() > 0 && !Objects.equals(customer.getAddress(), address)) {
            customer.setAddress(address);
        }

        if (password != null && password.length() > 0 && !Objects.equals(customer.getPassword(), password)) {
            customer.setPassword(password);
        }

        if (phoneNumber != null && phoneNumber.length() > 0 && !Objects.equals(customer.getPhoneNumber(), phoneNumber)) {
            customer.setPhoneNumber(phoneNumber);
        }

        if (email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
            if (customerOptional.isPresent()) {
                throw new BadRequestException("Email " + email + " already exists!");
            }
            customer.setEmail(email);
        }

        if (!Objects.equals(customer.getProductList(), productList)) {
            List<Product> oldProductList = customer.getProductList();
            customer.setProductList(productList);

            //update inventory table for old/new stock
            updateInventory(oldProductList, "add");
            updateInventory(productList, "substract");
        }

    }

    private void updateInventory(List<Product> productList, String operation) {
        if (productList != null && productList.size() > 0) {
            productList.stream().forEach(x -> {
                Optional<Inventory> inventoryOptional = inventoryRepository.findProductByProductName(x.getProductName());
                if (!inventoryOptional.isPresent()) {
                    throw new ObjectNotFoundException("Product named " + x.getProductName() + "does not exist in the inventory!");
                } else {
                    if ("add".equals(operation)) {
                        inventoryOptional.get().setQuantity(inventoryOptional.get().getQuantity() + x.getQuantity());
                    } else {
                        inventoryOptional.get().setQuantity(inventoryOptional.get().getQuantity() - x.getQuantity());
                    }
                }
            });
        }
    }
}
