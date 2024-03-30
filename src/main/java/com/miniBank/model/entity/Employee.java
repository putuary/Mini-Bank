package com.miniBank.model.entity;

import com.miniBank.utils.Enum.EGender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_employee")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private Date birthOfDate;
    @Column(columnDefinition = "TEXT")
    private String address;
    @Enumerated(EnumType.STRING)
    private EGender gender;
    private String phoneNumber;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<User> users;
}
