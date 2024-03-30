package com.miniBank.service.impl;

import com.miniBank.model.entity.BankAccount;
import com.miniBank.model.entity.Branch;
import com.miniBank.model.entity.Customer;
import com.miniBank.model.entity.User;
import com.miniBank.model.request.CustomerRequest;
import com.miniBank.model.response.CustomerResponse;
import com.miniBank.model.response.UserResponse;
import com.miniBank.repository.BankAccountRepository;
import com.miniBank.repository.BranchRepository;
import com.miniBank.repository.CustomerRepository;
import com.miniBank.repository.UserRepository;
import com.miniBank.service.CustomerService;
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

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final BankAccountRepository bankAccountRepository;
    private final  EGender[] listGender = EGender.values();

//    @Override
//    public List<CustomerResponse> getAllCustomer() {
//        List<Customer> customers = customerRepository.findAll();
//        List<CustomerResponse> customerResponses = new ArrayList<>();
//        for (Customer customer : customers) {
//            List<UserResponse> userAccounts = new ArrayList<>();
//            for (User user : customer.getUsers()) {
//                UserResponse userResponse = UserResponse.builder()
//                        .username(user.getUsername())
//                        .role(user.getRole().name())
//                        .accountNumber(user.getBankAccount().getAccountNumber())
//                        .branchBankName(user.getBankAccount().getBranch().getBranchName())
//                        .balance(user.getBankAccount().getBalance())
//                        .build();
//                userAccounts.add(userResponse);
//            }
//
//            // TODO Create Response
//            CustomerResponse response = CustomerResponse.builder()
//                    .id(customer.getId())
//                    .fullName(customer.getFullName())
//                    .birthDate(customer.getBirthOfDate())
//                    .address(customer.getAddress())
//                    .gender(customer.getGender().toString())
//                    .phoneNumber(customer.getPhoneNumber())
//                    .userAccounts(userAccounts)
//                    .build();
//            customerResponses.add(response);
//        }
//        return customerResponses;
//    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> {
                    List<UserResponse> userAccounts = customer.getUsers().stream()
                            .map(user -> UserResponse.builder()
                                    .username(user.getUsername())
                                    .role(user.getRole().name())
                                    .accountNumber(user.getBankAccount().getAccountNumber())
                                    .branchBankName(user.getBankAccount().getBranch().getBranchName())
                                    .balance(user.getBankAccount().getBalance())
                                    .build())
                            .collect(Collectors.toList());

                    return CustomerResponse.builder()
                            .id(customer.getId())
                            .fullName(customer.getFullName())
                            .birthDate(customer.getBirthOfDate())
                            .address(customer.getAddress())
                            .gender(customer.getGender().toString())
                            .phoneNumber(customer.getPhoneNumber())
                            .userAccounts(userAccounts)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        if(customerRepository.findById(id).isPresent()){
            Customer customer = customerRepository.findById(id).get();
            List<UserResponse> userAccounts = new ArrayList<>();
            for (User user : customer.getUsers()) {
                UserResponse userResponse = UserResponse.builder()
                        .username(user.getUsername())
                        .role(user.getRole().name())
                        .accountNumber(user.getBankAccount().getAccountNumber())
                        .branchBankName(user.getBankAccount().getBranch().getBranchName())
                        .balance(user.getBankAccount().getBalance())
                        .build();
                userAccounts.add(userResponse);
            }
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .fullName(customer.getFullName())
                    .birthDate(customer.getBirthOfDate())
                    .address(customer.getAddress())
                    .gender(customer.getGender().toString())
                    .phoneNumber(customer.getPhoneNumber())
                    .userAccounts(userAccounts)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    @Override
    @Transactional
    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        try {
            // TODO : Set Customer
            Customer customer = Customer.builder()
                    .fullName(customerRequest.getFullName())
                    .birthOfDate(customerRequest.getBirthDate())
                    .address(customerRequest.getAddress())
                    .gender(listGender[customerRequest.getGenderId() - 1])
                    .phoneNumber(customerRequest.getPhoneNumber())
                    .build();
            customerRepository.save(customer);

            // TODO Set Credential/user
            User user = User.builder()
                    .username(customerRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(customerRequest.getPassword()))
                    .role(ERole.CUSTOMER)
                    .customer(customer)
                    .build();
            userRepository.save(user);

            Branch branch = branchRepository.findById(customerRequest.getBranchId()).get();
            // TODO Set BankAccount
            BankAccount bankAccount = BankAccount.builder()
                    .accountNumber(customerRequest.getAccountNumber())
                    .branch(branch)
                    .balance(0L)
                    .user(user)
                    .build();
            bankAccountRepository.save(bankAccount);

            List<UserResponse> userAccounts = new ArrayList<>();
            UserResponse userResponse = UserResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().name())
                    .accountNumber(bankAccount.getAccountNumber())
                    .branchBankName(branch.getBranchName())
                    .balance(0L)
                    .build();
            userAccounts.add(userResponse);

            // TODO Create Response
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .fullName(customer.getFullName())
                    .birthDate(customer.getBirthOfDate())
                    .address(customer.getAddress())
                    .gender(customer.getGender().name())
                    .phoneNumber(customer.getPhoneNumber())
                    .userAccounts(userAccounts)
                    .build();
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exist");
        }
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
