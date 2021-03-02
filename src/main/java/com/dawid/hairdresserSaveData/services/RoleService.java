package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.Role;
import com.dawid.hairdresserSaveData.entity.UserData;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
}
