package com.miniBank.service.impl;

import com.miniBank.model.entity.*;
import com.miniBank.model.request.EmployeeRequest;
import com.miniBank.model.response.EmployeeAccountResponse;
import com.miniBank.model.response.EmployeeResponse;
import com.miniBank.repository.BankAccountRepository;
import com.miniBank.repository.EmployeeRepository;
import com.miniBank.repository.UserRepository;
import com.miniBank.service.EmployeeService;
import com.miniBank.utils.Enum.EGender;
import com.miniBank.utils.Enum.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountRepository bankAccountRepository;
    private final  EGender[] listGender = EGender.values();


    @Override
    public List<EmployeeResponse> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> {
                    List<EmployeeAccountResponse> employeeAccounts = employee.getUsers().stream()
                            .map(user -> EmployeeAccountResponse.builder()
                                    .username(user.getUsername())
                                    .role(user.getRole().name())
                                    .build())
                            .collect(Collectors.toList());

                    return EmployeeResponse.builder()
                            .id(employee.getId())
                            .fullName(employee.getFullName())
                            .birthDate(employee.getBirthOfDate())
                            .address(employee.getAddress())
                            .gender(employee.getGender().toString())
                            .phoneNumber(employee.getPhoneNumber())
                            .employeeAccounts(employeeAccounts)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getCustomerById(String id) {
        if(employeeRepository.findById(id).isPresent()){
            Employee employee = employeeRepository.findById(id).get();
            List<EmployeeAccountResponse> employeeAccounts = new ArrayList<>();
            for (User user : employee.getUsers()) {
                EmployeeAccountResponse employeeResponse = EmployeeAccountResponse.builder()
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .build();
                employeeAccounts.add(employeeResponse);
            }
            return EmployeeResponse.builder()
                    .id(employee.getId())
                    .fullName(employee.getFullName())
                    .birthDate(employee.getBirthOfDate())
                    .address(employee.getAddress())
                    .gender(employee.getGender().toString())
                    .phoneNumber(employee.getPhoneNumber())
                    .employeeAccounts(employeeAccounts)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    @Override
    @Transactional
    public EmployeeResponse saveCustomer(EmployeeRequest employeeRequest) {
        try {
            // TODO : Set Employee
            Employee employee = Employee.builder()
                    .fullName(employeeRequest.getFullName())
                    .birthOfDate(employeeRequest.getBirthDate())
                    .address(employeeRequest.getAddress())
                    .gender(listGender[employeeRequest.getGenderId() - 1])
                    .phoneNumber(employeeRequest.getPhoneNumber())
                    .build();

            employeeRepository.save(employee);

            // TODO Set Credential/user
            User user = User.builder()
                    .username(employeeRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(employeeRequest.getPassword()))
                    .role(ERole.ADMIN)
                    .employee(employee)
                    .build();
            userRepository.save(user);

            List<EmployeeAccountResponse> employeeAccounts = new ArrayList<>();
            EmployeeAccountResponse employeeAccountResponse = EmployeeAccountResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .build();
            employeeAccounts.add(employeeAccountResponse);

            // TODO Create Response
            return EmployeeResponse.builder()
                    .id(employee.getId())
                    .fullName(employee.getFullName())
                    .birthDate(employee.getBirthOfDate())
                    .address(employee.getAddress())
                    .gender(employee.getGender().name())
                    .phoneNumber(employee.getPhoneNumber())
                    .employeeAccounts(employeeAccounts)
                    .build();
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee Already Exist");
        }
    }

    @Override
    public EmployeeResponse updateCustomer(EmployeeRequest employeeRequest) {
        return null;
    }

    @Override
    public void deleteEmployee(String id) {
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    }
}
