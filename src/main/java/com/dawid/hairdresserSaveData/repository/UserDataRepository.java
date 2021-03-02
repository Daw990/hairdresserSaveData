package com.dawid.hairdresserSaveData.repository;

import com.dawid.hairdresserSaveData.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    List<UserData> findByFirstName(String name);
}
