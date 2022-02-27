package com.dandelic.BankManager.service;


import com.dandelic.BankManager.dto.BankAccountDto;
import com.dandelic.BankManager.dto.UserDto;
import com.dandelic.BankManager.model.BankAccount;
import com.dandelic.BankManager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SimpleMapper {
    UserDto convertUserToDto(User user);

    User convertDtoToUser(UserDto userDto);

    BankAccountDto convertBankAccountToDto(BankAccount bankAccount);

    @Mapping(target = "user", ignore = true)
    BankAccount convertDtoToBankAccount(BankAccountDto bankAccountDto);
}
