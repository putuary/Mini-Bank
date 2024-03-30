package com.miniBank.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionResponse {
    private String transactionId;
    private String accountNumber;
    private String fullName;
    private String transactionType;
    private Long amount;
    private Long balance;
    private String destAccountNumber;
    private String destFullName;
    private Date transactionDate;
}
