package com.dandelic.BankManager.dto;


import java.util.Set;

public class UserDto {
    private long id;
    private String email;
    private Set<BankAccountDto> bankAccountSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<BankAccountDto> getBankAccountSet() {
        return bankAccountSet;
    }

    public void setBankAccountSet(Set<BankAccountDto> bankAccountSet) {
        this.bankAccountSet = bankAccountSet;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", bankAccountSet=" + bankAccountSet +
                '}';
    }
}
