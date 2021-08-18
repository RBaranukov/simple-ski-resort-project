package com.example.ski_resort.baranukov.controller;

import com.example.ski_resort.baranukov.entity.User;
import com.example.ski_resort.baranukov.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ski-resort")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<User> showUser(@RequestParam String username){
        User user = userService.findUserByName(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user ){
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUserById(@RequestParam String username){
        userService.deleteByUsername(username);
        return new ResponseEntity<>("User was deleted", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("users/{roleName}")
    public ResponseEntity<String> addRoleToUser(@RequestParam String username, @PathVariable String roleName){
        userService.setRoleToUser(username, roleName);
        return ResponseEntity.ok("Added role to User");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send/user/{username}")
    public ResponseEntity<String> sendUser(@PathVariable String username){
        userService.sendUser(username);
        return new ResponseEntity<>("Send user", HttpStatus.OK);
    }
}
