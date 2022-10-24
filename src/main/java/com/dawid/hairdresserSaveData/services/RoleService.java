package com.dawid.hairdresserSaveData.services;

import com.dawid.hairdresserSaveData.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
}
