package com.example.ski_resort.baranukov.service.impl;

import com.example.ski_resort.baranukov.entity.Role;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.exception.RoleNotFoundException;
import com.example.ski_resort.baranukov.exception.UserAlreadyExistException;
import com.example.ski_resort.baranukov.exception.UserOrPasswordIncorrectException;
import com.example.ski_resort.baranukov.repository.UserRepository;
import com.example.ski_resort.baranukov.security.SecurityDetails;
import com.example.ski_resort.baranukov.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"user"})
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JmsTemplate jmsProducer;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if(optionalUser.isPresent()){
            return optionalUser.map(SecurityDetails::new).get();
        } else throw new UserOrPasswordIncorrectException();
    }

    @Override
    @Cacheable
    public User findUserByName(String username) {
        logger.info("Find user {}", username);
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else throw new UserOrPasswordIncorrectException();
    }

    @Override
    @CachePut(key = "#user.username")
    public User saveUser(User user) {
        if(!userRepository.existsByUsername(user.getUsername())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else throw new UserAlreadyExistException();
    }

    @Override
    @CacheEvict(key = "#username")
    public void deleteByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(UserOrPasswordIncorrectException::new);
        userRepository.delete(user);
    }

    @Override
    public void setRoleToUser(String username, String roleName) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(UserOrPasswordIncorrectException::new);
        if(roleName.equalsIgnoreCase(Role.ADMIN.name())){
            user.setRole(Role.ADMIN);
        } else if (roleName.equalsIgnoreCase(Role.MANAGER.name())){
            user.setRole(Role.MANAGER);
        } else throw new RoleNotFoundException();
        userRepository.save(user);
    }

    @Override
    public void sendUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(UserOrPasswordIncorrectException::new);
        jmsProducer.convertAndSend("queue.user", user);
    }
}
