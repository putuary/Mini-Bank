package com.miniBank.controller;

import com.miniBank.model.request.EmployeeRequest;
import com.miniBank.model.response.CommonResponse;
import com.miniBank.model.response.CustomerResponse;
import com.miniBank.model.response.EmployeeResponse;
import com.miniBank.service.EmployeeService;
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
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.EMPLOYEE)
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<EmployeeResponse>>> getAllEmployee() {
        List<EmployeeResponse> result= employeeService.getAllEmployee();
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ALL, "Employee");
        CommonResponse<List<EmployeeResponse>> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<EmployeeResponse>> getEmployeeById (@PathVariable String id) {
        EmployeeResponse result= employeeService.getCustomerById(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ID, "Employee", id);
        CommonResponse<EmployeeResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse result= employeeService.saveCustomer(employeeRequest);
        String message =String.format(Constant.MESSAGE_SUCCESS_INSERT, "Employee");
        CommonResponse<EmployeeResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> deleteEmployee (@PathVariable String id) {
        employeeService.deleteEmployee(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_DELETE, "Employee", id);
        CommonResponse<CustomerResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
