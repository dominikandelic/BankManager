package com.dandelic.BankManager.model;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue
    private Long id;

    private String ccPin;

    private double balance = 0;

    @NotBlank(message = "Account type is mandatory")
    private String accountType;

    protected BankAccount() {
    }

    public BankAccount(double balance, String accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCcPin() {
        return ccPin;
    }

    public void setCcPin(String ccPin) {
        this.ccPin = ccPin;
    }

}
