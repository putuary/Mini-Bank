package com.miniBank.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class CustomerResponse {
    private String id;
    private String fullName;
    private Date birthDate;
    private String address;
    private String gender;
    private String phoneNumber;
    private List<UserResponse> userAccounts;
}
