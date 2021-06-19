package com.PP311.mySpringBootApp.dao;

import com.PP311.mySpringBootApp.model.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findAll();
    void saveRole(Role role);
    void update(Role role);
    void deleteRole(Role role);
    Role findByRole(String role);
}
