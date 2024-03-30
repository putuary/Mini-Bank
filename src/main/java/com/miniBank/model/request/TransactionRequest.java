package com.miniBank.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
    private Integer transactionTypeId;
    private Integer paymentMethodId;
    private String destAccountNumber;
    private Long amount;
}
