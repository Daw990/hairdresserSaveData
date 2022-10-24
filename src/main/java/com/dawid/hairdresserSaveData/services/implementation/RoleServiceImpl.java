package com.dawid.hairdresserSaveData.services.implementation;

import com.dawid.hairdresserSaveData.entity.Role;
import com.dawid.hairdresserSaveData.repository.RoleRepository;
import com.dawid.hairdresserSaveData.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Optional<Role> findById(Long id) {
            return roleRepository.findById(id);
        }
}


