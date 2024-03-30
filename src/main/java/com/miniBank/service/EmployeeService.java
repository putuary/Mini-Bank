package com.miniBank.service;

import com.miniBank.model.entity.Employee;
import com.miniBank.model.request.CustomerRequest;
import com.miniBank.model.request.EmployeeRequest;
import com.miniBank.model.response.CustomerResponse;
import com.miniBank.model.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployee();
    EmployeeResponse getCustomerById(String id);
    EmployeeResponse saveCustomer(EmployeeRequest employeeRequest);
    EmployeeResponse updateCustomer(EmployeeRequest employeeRequest);
    void deleteEmployee(String id);
}
