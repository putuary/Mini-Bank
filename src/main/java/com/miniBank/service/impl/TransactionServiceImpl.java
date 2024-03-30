package com.miniBank.service.impl;

import com.miniBank.model.entity.AppUser;
import com.miniBank.model.entity.BankAccount;
import com.miniBank.model.entity.Transaction;
import com.miniBank.model.request.TransactionRequest;
import com.miniBank.model.response.TransactionResponse;
import com.miniBank.repository.BankAccountRepository;
import com.miniBank.repository.TransactionRepository;
import com.miniBank.service.TransactionService;
import com.miniBank.utils.Enum.EPaymentMethod;
import com.miniBank.utils.Enum.ETransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final  ETransactionType[] listTransactionType = ETransactionType.values();
    private final  EPaymentMethod[] listPaymentMethod = EPaymentMethod.values();
    @Override
    @Transactional
    public TransactionResponse saveTransaction(TransactionRequest transactionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        BankAccount account = bankAccountRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("User not found")));
        BankAccount destAccount = bankAccountRepository.findByAccountNumber(transactionRequest.getDestAccountNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Account not found")));

        // TODO : Set Transaction
        Transaction transaction = Transaction.builder()
                .bankAccount(account)
                .transactionType(listTransactionType[transactionRequest.getTransactionTypeId() - 1])
                .transDate(new Date())
                .amount(transactionRequest.getAmount())
                .paymentMethod(listPaymentMethod[transactionRequest.getPaymentMethodId() - 1])
                .destBankAccount(destAccount)
                .build();
        transactionRepository.save(transaction);

        // TODO : Set Account
        Long balanceNow = account.getBalance();
        if(transaction.getPaymentMethod() == EPaymentMethod.TRANSFER) {
            balanceNow -= transactionRequest.getAmount();
            bankAccountRepository.updateSaldoAccount(balanceNow, account.getId());
        }

        // TODO : Set Dest Account
        bankAccountRepository.updateSaldoAccount((destAccount.getBalance() + transactionRequest.getAmount()), destAccount.getId());

        if(account.getId().equals(destAccount.getId())) {
            balanceNow += transactionRequest.getAmount();
        }

        // TODO : Set Response
        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .accountNumber(account.getAccountNumber())
//                .fullName(account.getUser())
                .transactionType(transaction.getTransactionType().name())
                .amount(transaction.getAmount())
                .balance(balanceNow)
                .destAccountNumber(destAccount.getAccountNumber())
//                .destFullName(destAccount.getUser().getCustomer().getFullName())
                .transactionDate(transaction.getTransDate())
                .build();
    }

    @Override
    public List<TransactionResponse> getAllTransaction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        BankAccount account = bankAccountRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("User not found")));

        List<Transaction> transactions = transactionRepository.findAllAccountId(account.getId());
        return transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .transactionId(transaction.getId())
                        .accountNumber(transaction.getBankAccount().getAccountNumber())
                        .transactionType(transaction.getTransactionType().name())
                        .amount(transaction.getAmount())
                        .destAccountNumber(transaction.getDestBankAccount().getAccountNumber())
                        .transactionDate(transaction.getTransDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse getTransactionById(String id) {
        System.out.println(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = (AppUser) authentication.getPrincipal();
        BankAccount account = bankAccountRepository.findByUserId(user.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("User not found")));
        System.out.println(account.getId());

        if(transactionRepository.findById(id, account.getId()).isPresent()) {
            Transaction transaction = transactionRepository.findById(id, account.getId()).get();

            return TransactionResponse.builder()
                    .transactionId(transaction.getId())
                    .accountNumber(transaction.getBankAccount().getAccountNumber())
                    .transactionType(transaction.getTransactionType().name())
                    .amount(transaction.getAmount())
                    .destAccountNumber(transaction.getDestBankAccount().getAccountNumber())
                    .transactionDate(transaction.getTransDate())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,("Transaction not found"));
        }
    }
}
