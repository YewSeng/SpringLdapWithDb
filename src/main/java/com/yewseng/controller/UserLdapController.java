package com.yewseng.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yewseng.dto.User;
import com.yewseng.service.UserService;

@Controller
@RequestMapping("/userLdap")
public class UserLdapController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/addUserForm")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }
    
    @PostMapping("/addUser")
    public String addUser(User user) {
        userService.createUser(user);
        return "success";
    }
    
    @GetMapping("/modifyUserForm/{uid}")
    public String modifyUserForm(@PathVariable String uid, Model model) {
        User user = userService.getUserById(uid);
        if (user != null) {
            model.addAttribute("user", user);
            return "modifyUser";
        } else {
            return "userNotFound";
        }
    }

    @PostMapping("/modifyUser/{uid}")
    public String modifyUser(@PathVariable String uid, User modifiedUser) {
        userService.updateUser(uid, modifiedUser);
        return "redirect:/userLdap/userList";
    }
    
    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "userList";
    }
}
