package com.neteasy.senior.controller;

import com.neteasy.senior.model.User;
import com.neteasy.senior.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    private List<User> getOrderInfo() {
        List<User> res = userService.findUser();
        return res;
    }

    @PostMapping
    private void addUser(@RequestBody  User user){
        userService.addUser(user);
    }

}
