package com.dandelic.BankManager.dto;

import lombok.Data;

@Data
public class BankAccountDto {
    private Long id;

    private double balance;

    private String accountType;
}
