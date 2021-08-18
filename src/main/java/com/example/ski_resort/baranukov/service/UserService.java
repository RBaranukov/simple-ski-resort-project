package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.User;

public interface UserService {

    User saveUser(User user);

    void deleteByUsername(String username);

    void setRoleToUser(String username, String roleName);

    User findUserByName(String username);

    void sendUser(String username);
}
