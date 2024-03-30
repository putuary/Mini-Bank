package com.miniBank.service.impl;

import com.miniBank.model.entity.AppUser;
import com.miniBank.model.entity.Customer;
import com.miniBank.model.entity.Employee;
import com.miniBank.model.entity.User;
import com.miniBank.model.request.AuthRequest;
import com.miniBank.model.request.LoginRequest;
import com.miniBank.model.response.LoginResponse;
import com.miniBank.repository.EmployeeRepository;
import com.miniBank.repository.UserRepository;
import com.miniBank.security.JwtUtil;
import com.miniBank.service.AuthService;
import com.miniBank.service.CustomerService;
import com.miniBank.utils.Enum.EGender;
import com.miniBank.utils.Enum.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final  EGender[] listGender = EGender.values();
    @Override
    @Transactional
    public User registerEmployee(AuthRequest authRequest) {
        try {
//            System.out.println(authRequest);
            // TODO : Set Employee
            Employee employee = Employee.builder()
                    .fullName(authRequest.getFullName())
                    .birthOfDate(authRequest.getBirthDate())
                    .address(authRequest.getAddress())
                    .gender(listGender[authRequest.getGenderId() - 1])
                    .phoneNumber(authRequest.getPhoneNumber())
                    .build();
            employeeRepository.save(employee);

            // TODO Set Credential/user
            User user = User.builder()
                    .username(authRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(ERole.ADMIN)
                    .employee(employee)
                    .build();
            userRepository.save(user);

            // TODO Create Response
            return user;
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exist");
        }
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername().toLowerCase(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
