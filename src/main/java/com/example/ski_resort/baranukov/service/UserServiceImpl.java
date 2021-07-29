package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.Role;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.exception.RoleNotFoundException;
import com.example.ski_resort.baranukov.exception.UserNotFoundException;
import com.example.ski_resort.baranukov.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final @NonNull UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByName(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else throw new UserNotFoundException();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public void setRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if(roleName.equalsIgnoreCase(Role.ADMIN.name())){
            user.setRole(Role.ADMIN);
        } else if (roleName.equalsIgnoreCase(Role.MANAGER.name())){
            user.setRole(Role.MANAGER);
        } else throw new RoleNotFoundException();
        userRepository.save(user);
    }
}
