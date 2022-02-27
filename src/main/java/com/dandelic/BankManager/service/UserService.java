package com.dandelic.BankManager.service;

import com.dandelic.BankManager.dto.UserDto;
import com.dandelic.BankManager.model.User;
import com.dandelic.BankManager.repository.BankAccountRepository;
import com.dandelic.BankManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    private SimpleMapper mapper;

    public List<UserDto> getUsers() {
        List<UserDto> userList = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll();
        userIterable.forEach((user) -> userList.add(mapper.convertUserToDto(user)));
        return userList;
    }

    public UserDto getUser(Long userId) {
        return mapper.convertUserToDto(userRepository.findById(userId).get());
    }

    public UserDto addUser(UserDto userDto) {
        User user = mapper.convertDtoToUser(userDto);
        user.getBankAccountSet().forEach(bankAccount -> bankAccount.setUser(user));
        userRepository.save(user);
        return mapper.convertUserToDto(user);
    }

}
