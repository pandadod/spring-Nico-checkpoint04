package com.checkpoint04.checkpoint04.controller;

import com.checkpoint04.checkpoint04.entity.RecipeFav;
import com.checkpoint04.checkpoint04.entity.User;
import com.checkpoint04.checkpoint04.repository.RecipeFavRepository;
import com.checkpoint04.checkpoint04.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeFavRepository recipeFavRepository;

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/user/login")
    public User login(@RequestBody User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @PostMapping("/user/{idUser}/recipe/{idRecipe}")
    public User addRecipeFavorite(@PathVariable Long idUser, @PathVariable Long idRecipe) {
        User user = userRepository.findById(idUser).get();
        RecipeFav recipeFav = recipeFavRepository.findById(idRecipe).get();
        user.getRecipeFavs().add(recipeFav);
        recipeFav.getUsers().add(user);
        recipeFavRepository.save(recipeFav);
        return userRepository.save(user);
    }

    @DeleteMapping("/user/{idUser}/recipe/{idRecipe}")
    public User deleteRecipeFavorite(@PathVariable Long idUser, @PathVariable Long idRecipe) {
        User user = userRepository.findById(idUser).get();
        RecipeFav recipeFav = recipeFavRepository.findById(idRecipe).get();
        user.getRecipeFavs().remove(recipeFav);
        recipeFav.getUsers().remove(user);
        recipeFavRepository.save(recipeFav);
        return userRepository.save(user);
    }
}