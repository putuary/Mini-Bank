package com.miniBank.controller;

import com.miniBank.model.entity.Branch;
import com.miniBank.model.request.CustomerRequest;
import com.miniBank.model.response.CommonResponse;
import com.miniBank.model.response.CustomerResponse;
import com.miniBank.service.CustomerService;
import com.miniBank.utils.constant.ApiPathConstant;
import com.miniBank.utils.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.CUSTOMER)
@PreAuthorize("hasAuthority('ADMIN')")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer () {
        List<CustomerResponse> result= customerService.getAllCustomer();
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ALL, "Customer");
        CommonResponse<List<CustomerResponse>> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById (@PathVariable String id) {
        CustomerResponse result= customerService.getCustomerById(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ID, "Customer", id);
        CommonResponse<CustomerResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse result= customerService.saveCustomer(customerRequest);
        String message =String.format(Constant.MESSAGE_SUCCESS_INSERT, "Customer");
        CommonResponse<CustomerResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_DELETE, "Customer", id);
        CommonResponse<CustomerResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
