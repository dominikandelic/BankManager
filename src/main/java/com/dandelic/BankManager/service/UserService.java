package com.dandelic.BankManager.service;

import com.dandelic.BankManager.dto.BankAccountDto;
import com.dandelic.BankManager.dto.UserDto;
import com.dandelic.BankManager.model.BankAccount;
import com.dandelic.BankManager.model.User;
import com.dandelic.BankManager.repository.BankAccountRepository;
import com.dandelic.BankManager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDto> getUsers() {
        List<UserDto> userList = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll();
        userIterable.forEach((user) -> userList.add(convertUserToDto(user)));
        return userList;
    }

    public UserDto getUser(Long userId) {
        return modelMapper.map(userRepository.findById(userId).get(), UserDto.class);
    }

    public Set<BankAccountDto> getBankAccounts(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getBankAccountSet().stream().map(this::convertBankAccountToDto).collect(Collectors.toSet());
    }

    public UserDto addUser(UserDto userDto) {
        User user = convertDtoToUser(userDto);
        userRepository.save(user);
        return convertUserToDto(user);
    }

    public BankAccountDto addBankAccount(BankAccountDto bankAccountDto, Long userId) {
        BankAccount bankAccount = convertDtoToBankAccount(bankAccountDto);
        bankAccountRepository.save(bankAccount);
        User user = userRepository.findById(userId).get();
        user.getBankAccountSet().add(bankAccount);
        userRepository.save(user);
        return convertBankAccountToDto(bankAccount);
    }

    public BankAccountDto updateBankAccount(BankAccountDto bankAccountDto, Long userId) {
        BankAccount bankAccount = convertDtoToBankAccount(bankAccountDto);
        bankAccountRepository.save(bankAccount);
        User user = userRepository.findById(userId).get();
        user.getBankAccountSet().remove(bankAccount);
        user.getBankAccountSet().add(bankAccount);
        userRepository.save(user);
        return convertBankAccountToDto(bankAccount);
    }

    public boolean deleteBankAccount (Long userId, Long bankAccountId) {
        if(!isValidAccount(userId, bankAccountId)) throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        User user = userRepository.findById(userId).get();
        user.getBankAccountSet().remove(bankAccountRepository.findById(bankAccountId).get());
        userRepository.save(user);
        return true;
    }

    public BankAccountDto addFundsToBankAccount(Long bankAccountId, Long userId, Double amount) {
        if(!isValidAccount(userId, bankAccountId)) throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).get();
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
        return convertBankAccountToDto(bankAccount);
    }

    public BankAccountDto transferFunds(Long userId, Long bankAccountIdFrom, Long bankAccountIdTo, Double amount) {
        if(!isValidAccount(userId, bankAccountIdFrom)) throw new IllegalArgumentException("Account is not valid or the user is not the owner of the account");
        if(!enoughFunds(bankAccountIdFrom, amount)) throw new IllegalArgumentException("User does not have enough funds in his account.");
        if(!receivingAccountExists(bankAccountIdTo)) throw new IllegalArgumentException(("Receiving account does not exist"));
        BankAccount from = bankAccountRepository.findById(bankAccountIdFrom).get();
        BankAccount to = bankAccountRepository.findById((bankAccountIdTo)).get();
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
        return convertBankAccountToDto(from);
    }

    // Validators

    private boolean isValidAccount(Long userId, Long bankAccountIdFrom){
        Set<BankAccount> bankAccountSet = userRepository.findById(userId).get().getBankAccountSet();
        for(BankAccount bankAccount : bankAccountSet) {
            if(bankAccount.getId() == bankAccountIdFrom) return true;
        }
        return false;
    }

    private boolean receivingAccountExists(Long bankAccountIdTo){
        return bankAccountRepository.findById(bankAccountIdTo).isPresent();
    }

    private boolean enoughFunds(Long bankAccountIdFrom, Double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountIdFrom).get();
        if (bankAccount.getBalance() - amount > 0){
            return true;
        } else return false;
    }

    // Converters

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertDtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public BankAccountDto convertBankAccountToDto(BankAccount bankAccount) { return modelMapper.map(bankAccount, BankAccountDto.class); }

    public BankAccount convertDtoToBankAccount(BankAccountDto bankAccountDto) { return modelMapper.map(bankAccountDto, BankAccount.class); }
}
