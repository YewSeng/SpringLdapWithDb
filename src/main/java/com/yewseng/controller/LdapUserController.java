package com.yewseng.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yewseng.dto.User;
import com.yewseng.service.UserService;

@RestController
@RequestMapping("/users")
public class LdapUserController {

    @Autowired
    private UserService userService;
  
    @GetMapping("/")
    public String index() {
        return "Welcome to the home page!";
    }
    
    @GetMapping("/getUserDetails")
    public String getUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // access user details
        String username = userDetails.getUsername();
        boolean accNonExpired = userDetails.isAccountNonExpired();
        return "UserDetails: " + username + "\n Account Non Expired: " + accNonExpired;
    }
    
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/getUserByUid/{uid}")
    public User getUserByUid(@PathVariable String uid) {
        return userService.getUserById(uid);
    }
    
    @PutMapping("/updateUser/{uid}")
    public ResponseEntity<String> updateUser(@PathVariable String uid, @RequestBody User modifiedUser) {
        User updatedUser = userService.updateUser(uid, modifiedUser);
        if (updatedUser != null) {
            return ResponseEntity.ok("User with UID " + uid + " has been updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
  
    @GetMapping("/deleteUser/{uid}")
    public String deleteUser(@PathVariable String uid) {
        userService.deleteUser(uid);
        return "User with UID " + uid + " has been deleted from database and LDAP Server";
    }
}
