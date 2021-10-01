package com.example.ski_resort.baranukov.service;

import com.example.ski_resort.baranukov.entity.Role;
import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.exception.UserAlreadyExistException;
import com.example.ski_resort.baranukov.exception.UserOrPasswordIncorrectException;
import com.example.ski_resort.baranukov.repository.UserRepository;
import com.example.ski_resort.baranukov.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserByUsernameAndEqualUsername() {
        User user = new User(1L, "Anna1996", "qwerty123", true, Role.MANAGER);

        Mockito.when(userRepository.findUserByUsername("Anna1996")).thenReturn(Optional.of(user));

        User user1 = userService.findUserByName("Anna1996");

        assertEquals(user1.getUsername(), user.getUsername());
    }

    @Test
    public void saveGuestAndEqualUsername(){
        User input = new User(1L, "Anna1996", bCryptPasswordEncoder.encode("qwerty123"), true, Role.MANAGER);
        User returned = new User(1L, "Anna1996", bCryptPasswordEncoder.encode("qwerty123"), true, Role.MANAGER);

        Mockito.when(userRepository.save(input)).thenReturn(returned);

        User result = userService.saveUser(input);

        assertEquals("Anna1996", result.getUsername());
    }

    @Test
    public void deleteUserTest(){
        User input = new User(1L, "Anna1996", bCryptPasswordEncoder.encode("qwerty123"), true, Role.MANAGER);

        Mockito.when(userRepository.findUserByUsername("Anna1996")).thenReturn(Optional.of(input));
        Mockito.doNothing().when(userRepository).delete(input);

        userService.deleteByUsername("Anna1996");
    }

    @Test(expected = UserOrPasswordIncorrectException.class)
    public void getUserByUsernameAndThrowUserNotFoundException(){
        Mockito.doThrow(new UserOrPasswordIncorrectException()).when(userRepository).findUserByUsername("Anna");
        userService.findUserByName("Anna");
    }

    @Test(expected = UserAlreadyExistException.class)
    public void getUserByUsernameAndThrowUserAlreadyExistNotFoundException(){
        Mockito.doThrow(new UserAlreadyExistException()).when(userRepository).findUserByUsername("Anna");
        userService.findUserByName("Anna");
    }
}
