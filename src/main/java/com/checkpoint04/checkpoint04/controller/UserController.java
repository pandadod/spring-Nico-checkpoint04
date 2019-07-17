package com.checkpoint04.checkpoint04.controller;

import com.checkpoint04.checkpoint04.entity.User;
import com.checkpoint04.checkpoint04.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/user/login")
    public User login(@RequestBody User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @PutMapping("/user/{idUser}")
    public User addRecipeFavorite(@PathVariable Long idUser, @RequestBody User userUpdate) {
        User user = userRepository.findById(idUser).get();
        user.setRecipeFavs(userUpdate.getRecipeFavs());
        return userRepository.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}