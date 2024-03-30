package com.miniBank.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeAccountResponse {
    private String username;
    private String role;
}
