package com.miniBank.model.entity;

import com.miniBank.utils.Enum.EPaymentMethod;
import com.miniBank.utils.Enum.ETransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private ETransactionType transactionType;
    private Date transDate;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "dest_account_id")
    private BankAccount destBankAccount;
}
