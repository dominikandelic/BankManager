package com.dandelic.BankManager.controller;

import com.dandelic.BankManager.service.UserService;
import com.dandelic.BankManager.dto.BankAccountDto;
import com.dandelic.BankManager.dto.UserDto;
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
        return userService.getBankAccounts(userId);
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
        return userService.addBankAccount(bankAccountDto, userId);
    }

    @PutMapping("/{userId}/bankaccounts/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto updateBankAccount(@Valid @RequestBody BankAccountDto bankAccountDto, @PathVariable Long userId, @PathVariable Long bankAccountId) {
        Preconditions.checkNotNull(bankAccountDto);
        return userService.updateBankAccount(bankAccountDto, userId);
    }

    @PutMapping("/{userId}/bankaccounts/{bankAccountId}/add")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDto addFundsToBankAccount(@RequestBody Map<String, Double> jsonRequest, @PathVariable Long userId, @PathVariable Long bankAccountId) {
        Preconditions.checkNotNull(jsonRequest);
        return userService.addFundsToBankAccount(bankAccountId, userId, jsonRequest.get("amount"));
    }

    @PutMapping("/{userId}/bankaccounts/transferFrom/{bankAccountIdFrom}/to/{bankAccountIdTo}")
    @ResponseStatus(HttpStatus.OK)
    // Provjere
    // Je li njegov račun i postoji li
    // Ima li dovoljno sredstava da prenese
    public BankAccountDto transferFunds(@RequestBody Map<String, Double> jsonRequest, @PathVariable Long userId, @PathVariable Long bankAccountIdFrom, @PathVariable Long bankAccountIdTo) {
        Preconditions.checkNotNull(jsonRequest);
        return userService.transferFunds(userId, bankAccountIdFrom, bankAccountIdTo, jsonRequest.get("amount"));
    }

    @DeleteMapping("/{userId}/bankaccounts/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    // Nekakav secret ubaciti u ResponseBody kako bi se potvrdilo brisanje?
    public boolean deleteBankAccount(@PathVariable Long userId, @PathVariable Long bankAccountId){
        return userService.deleteBankAccount(userId, bankAccountId);

    }


}
