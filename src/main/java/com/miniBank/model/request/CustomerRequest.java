package com.miniBank.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomerRequest {
    private String username;
    private String password;
    private String fullName;
    private Date birthDate;
    private String address;
    private Integer genderId;
    private String phoneNumber;
    private String accountNumber;
    private String branchId;
}
