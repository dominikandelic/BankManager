package com.dandelic.BankManager.service;

import com.dandelic.BankManager.dto.BankAccountDto;
import com.dandelic.BankManager.model.BankAccount;
import com.dandelic.BankManager.model.User;
import com.dandelic.BankManager.repository.BankAccountRepository;
import com.dandelic.BankManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    SimpleMapper mapper;

    public Set<BankAccountDto> getBankAccounts(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getBankAccountSet().stream().map(mapper::convertBankAccountToDto).collect(Collectors.toSet());
    }

    public BankAccountDto addBankAccount(BankAccountDto bankAccountDto, Long userId) {
        BankAccount bankAccount = mapper.convertDtoToBankAccount(bankAccountDto);
        User user = userRepository.findById(userId).get();
        bankAccount.setUser(user);
        bankAccountRepository.save(bankAccount);
        user.getBankAccountSet().add(bankAccount);
        // TODO Check - ID doesn't generate if only saved to userRepo
        return mapper.convertBankAccountToDto(bankAccount);
    }

    public BankAccountDto updateBankAccount(BankAccountDto bankAccountDto, Long userId) {
        BankAccount bankAccount = mapper.convertDtoToBankAccount(bankAccountDto);
        User user = userRepository.findById(userId).get();
        bankAccount.setUser(user);
        user.getBankAccountSet().remove(bankAccount);
        user.getBankAccountSet().add(bankAccount);
        return mapper.convertBankAccountToDto(bankAccount);
    }

    public boolean deleteBankAccount(Long userId, Long bankAccountId) {
        if (!isValidAccount(userId, bankAccountId))
            throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        User user = userRepository.findById(userId).get();
        user.getBankAccountSet().removeIf(bankAccount -> bankAccount.getId() == bankAccountId);
        return true;
    }

    public BankAccountDto addFundsToBankAccount(Long bankAccountId, Long userId, Double amount) {
        if (!isValidAccount(userId, bankAccountId))
            throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).get();
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        return mapper.convertBankAccountToDto(bankAccount);
    }

    public BankAccountDto transferFunds(Long userId, Long bankAccountIdFrom, Long bankAccountIdTo, Double amount) {
        if (!isValidAccount(userId, bankAccountIdFrom))
            throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        if (!enoughFunds(bankAccountIdFrom, amount))
            throw new IllegalArgumentException("User does not have enough funds in his account.");
        if (!receivingAccountExists(bankAccountIdTo))
            throw new IllegalArgumentException(("Receiving account does not exist"));
        BankAccount from = bankAccountRepository.findById(bankAccountIdFrom).get();
        BankAccount to = bankAccountRepository.findById((bankAccountIdTo)).get();
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        return mapper.convertBankAccountToDto(from);
    }

    // Validators

    private boolean isValidAccount(Long userId, Long bankAccountIdFrom) {
        Set<BankAccount> bankAccountSet = userRepository.findById(userId).get().getBankAccountSet();
        for (BankAccount bankAccount : bankAccountSet) {
            if (bankAccount.getId() == bankAccountIdFrom) return true;
        }
        return false;
    }

    private boolean receivingAccountExists(Long bankAccountIdTo) {
        return bankAccountRepository.findById(bankAccountIdTo).isPresent();
    }

    private boolean enoughFunds(Long bankAccountIdFrom, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountIdFrom).get();
        if (bankAccount.getBalance() - amount > 0) {
            return true;
        } else return false;
    }
}
