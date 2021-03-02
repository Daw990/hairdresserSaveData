package com.dawid.hairdresserSaveData.repository;

import com.dawid.hairdresserSaveData.entity.User;
import com.dawid.hairdresserSaveData.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);
    Optional<User> findByConfirmationToken(String token);
    User findByUserData(UserData userData);
}
