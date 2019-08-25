package com.application.controller;

import com.application.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/service1")
public class AppController {

    private List<User> users = new ArrayList<User>(Arrays.asList(
            new User("admin","password"),
            new User("test","test"),
            new User("infra","infra")
    ));

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return users;
    }

    @GetMapping("/users/{userId}")
    public Optional<User> getUser(@PathVariable("userId") String username){
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findAny();
    }

    @DeleteMapping("/users/{userId}")
    public List<User> deleteUser(@PathVariable("userId") String username){
        User usr = getUser(username).get();
        if(usr != null){
            users.remove(usr);
        }
        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<List<User>> registerUser(@RequestBody User user){
        users.add(user);
        return new ResponseEntity<List<User>>(users, HttpStatus.CREATED);
    }
}

