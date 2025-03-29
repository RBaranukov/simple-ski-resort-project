package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<User> showUser(@RequestParam final String username) {
        User user = userService.findUserByName(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody final User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestParam final String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity<>("User was deleted", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{roleName}")
    public ResponseEntity<String> addRoleToUser(
            @RequestParam final String username,
            @PathVariable final String roleName
    ) {
        userService.setRoleToUser(username, roleName);
        return ResponseEntity.ok("Added role to User");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send/{username}")
    public ResponseEntity<String> sendUser(@PathVariable final String username) {
        userService.sendUser(username);
        return new ResponseEntity<>("Send user", HttpStatus.OK);
    }
}
