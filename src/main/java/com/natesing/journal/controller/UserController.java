package com.natesing.journal.controller;

import com.natesing.journal.entity.User;
import com.natesing.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAllEntries();
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveEntry(user);
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updatedUser(@RequestBody User user) {
        User byUserName = userService.findByUserName(user.getUserName());
        if (byUserName != null) {
            byUserName.setUserName(user.getUserName());
            byUserName.setPassword(user.getPassword());
            userService.saveEntry(byUserName);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
