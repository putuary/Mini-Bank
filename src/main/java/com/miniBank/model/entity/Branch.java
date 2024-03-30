package com.miniBank.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "mst_branch")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String branchName;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;
}
