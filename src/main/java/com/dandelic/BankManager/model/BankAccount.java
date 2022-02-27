package com.dandelic.BankManager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;

    private String ccPin;

    private double balance = 0;

    @NotBlank(message = "Account type is mandatory")
    private String accountType;


    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;


    public BankAccount(double balance, String accountType, User user) {
        this.balance = balance;
        this.accountType = accountType;
        this.user = user;
    }

}
