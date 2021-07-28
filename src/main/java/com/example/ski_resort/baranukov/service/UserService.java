package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.User;

public interface UserService {

    void saveUser(User user);

    void delete(Long id);

    void setRoleToUser(Long userId, String roleName);

    User findUserByName(String username);
}
