package com.dandelic.BankManager.config;

import com.dandelic.BankManager.model.BankAccount;
import com.dandelic.BankManager.model.User;
import com.dandelic.BankManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Override
    // Just for testing, appends data to db
    public void run(String... args) throws Exception {
        userRepository.save(new User("adminUser", "johndoe@gmail.com", "$2a$16$WUuXFP.gXQIxvjqHnEdC8uzKRl0YgKLzrWPMNoJVOfnuWkpYmPyqe", "ROLE_ADMIN,ROLE_USER", true, Set.of(new BankAccount(50, "checking"), new BankAccount(1000, "checking"))));
        userRepository.save(new User("regularUser", "jackdoe@gmail.com", "$2a$16$WUuXFP.gXQIxvjqHnEdC8uzKRl0YgKLzrWPMNoJVOfnuWkpYmPyqe", "ROLE_USER", true, Set.of(new BankAccount(250, "checking"), new BankAccount(55500, "checking"))));
    }
}
