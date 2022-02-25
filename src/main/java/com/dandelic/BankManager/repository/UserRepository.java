package com.dandelic.BankManager.repository;

import com.dandelic.BankManager.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
