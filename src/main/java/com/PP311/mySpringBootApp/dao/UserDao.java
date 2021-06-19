package com.PP311.mySpringBootApp.dao;

import com.PP311.mySpringBootApp.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    void saveUser(User user);
    void update(long id, User user);
    void update(User user);
    void deleteById(long id);
    User findById(long id);
    User findByLogin(String login);
}
