package com.miniBank.controller;

import com.miniBank.model.entity.User;
import com.miniBank.model.request.AuthRequest;
import com.miniBank.model.request.TransactionRequest;
import com.miniBank.model.response.CommonResponse;
import com.miniBank.model.response.TransactionResponse;
import com.miniBank.service.TransactionService;
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
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.TRANSACTION)
@PreAuthorize("hasAuthority('CUSTOMER')")
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> saveTransaction (@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse result = transactionService.saveTransaction(transactionRequest);
        String message =String.format(Constant.MESSAGE_SUCCESS_INSERT, "transaction");
        CommonResponse<TransactionResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAllTransaction () {
        List<TransactionResponse> result = transactionService.getAllTransaction();
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ALL, "transaction");
        CommonResponse<List<TransactionResponse>> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById (@PathVariable String id) {
        TransactionResponse result = transactionService.getTransactionById(id);
        String message =String.format(Constant.MESSAGE_SUCCESS_GET_ID, "transaction", id);
        CommonResponse<TransactionResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
