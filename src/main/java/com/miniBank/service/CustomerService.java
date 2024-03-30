package com.miniBank.service;

import com.miniBank.model.request.CustomerRequest;
import com.miniBank.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(String id);
    CustomerResponse saveCustomer(CustomerRequest customerRequest);
    CustomerResponse updateCustomer(CustomerRequest customerRequest);
    void deleteCustomer(String id);
}
