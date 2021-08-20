package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.Role;
import com.tusofia.project.carshop.database.repository.RoleRepository;
import com.tusofia.project.carshop.exception.RoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String name){
        return this.roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role with given name was not found!"));


    }
}
