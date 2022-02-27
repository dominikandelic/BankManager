package com.dandelic.BankManager.controller;

import com.dandelic.BankManager.dto.BankAccountDto;
import com.dandelic.BankManager.dto.UserDto;
import com.dandelic.BankManager.service.BankAccountService;
import com.dandelic.BankManager.service.UserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;


    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/{userId}/bankaccounts/")
    public Set<BankAccountDto> getBankAccounts(@PathVariable Long userId) {
        return bankAccountService.getBankAccounts(userId);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        Preconditions.checkNotNull(userDto);
        return userService.addUser(userDto);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountDto addBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto, @PathVariable Long userId) {
        Preconditions.checkNotNull(bankAccountDto);
        return bankAccountService.addBankAccount(bankAccountDto, userId);
    }

    @PutMapping("/{userId}/bankaccounts/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto updateBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto, @PathVariable Long userId, @PathVariable Long bankAccountId) {
        Preconditions.checkNotNull(bankAccountDto);
        return bankAccountService.updateBankAccount(bankAccountDto, userId);
    }

    @PutMapping("/{userId}/bankaccounts/{bankAccountId}/add")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto addFundsToBankAccount(@RequestBody Map<String, Double> jsonRequest, @PathVariable Long userId, @PathVariable Long bankAccountId) {
        Preconditions.checkNotNull(jsonRequest);
        return bankAccountService.addFundsToBankAccount(bankAccountId, userId, jsonRequest.get("amount"));
    }

    @PutMapping("/{userId}/bankaccounts/transferFrom/{bankAccountIdFrom}/to/{bankAccountIdTo}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto transferFunds(@RequestBody Map<String, Double> jsonRequest, @PathVariable Long userId, @PathVariable Long bankAccountIdFrom, @PathVariable Long bankAccountIdTo) {
        Preconditions.checkNotNull(jsonRequest);
        return bankAccountService.transferFunds(userId, bankAccountIdFrom, bankAccountIdTo, jsonRequest.get("amount"));
    }

    @DeleteMapping("/{userId}/bankaccounts/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    // TODO Insert secret as a way to protect from accidentally deleting bank account
    public boolean deleteBankAccount(@PathVariable Long userId, @PathVariable Long bankAccountId) {
        return bankAccountService.deleteBankAccount(userId, bankAccountId);

    }


}
