package com.PP311.mySpringBootApp.service;

import com.PP311.mySpringBootApp.model.Role;

import java.util.Set;

public interface RoleService {
    boolean add(Role role);
    Role getById(Long id);
    Role getByName(String name);
    boolean update(Role role);
    Set<Role> findAll();
}
