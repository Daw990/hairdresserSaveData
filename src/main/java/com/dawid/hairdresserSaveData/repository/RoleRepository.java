package com.dawid.hairdresserSaveData.repository;


import com.dawid.hairdresserSaveData.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role);
}
