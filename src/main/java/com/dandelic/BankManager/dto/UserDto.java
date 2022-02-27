package com.dandelic.BankManager.dto;


import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private long id;
    private String email;
    private Set<BankAccountDto> bankAccountSet;
}
