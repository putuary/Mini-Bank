package com.miniBank.model.response;

import com.miniBank.model.entity.BankAccount;
import com.miniBank.model.entity.Customer;
import com.miniBank.model.entity.Employee;
import com.miniBank.utils.Enum.ERole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String role;
    private String accountNumber;
    private String branchBankName;
    private Long balance;
}
