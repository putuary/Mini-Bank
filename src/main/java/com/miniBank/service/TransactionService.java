package com.miniBank.service;

import com.miniBank.model.request.TransactionRequest;
import com.miniBank.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse saveTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse> getAllTransaction();
    TransactionResponse getTransactionById(String id);
}
