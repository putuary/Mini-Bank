package com.miniBank.model.response;

import lombok.*;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String role;
}
