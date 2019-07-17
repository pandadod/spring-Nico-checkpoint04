package com.checkpoint04.checkpoint04.controller;

import com.checkpoint04.checkpoint04.entity.RecipeFav;
import com.checkpoint04.checkpoint04.entity.User;
import com.checkpoint04.checkpoint04.repository.RecipeFavRepository;
import com.checkpoint04.checkpoint04.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@RestController
public class RecipeFavController {

    @Autowired
    RecipeFavRepository recipeFavRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/recipes")
    public List<RecipeFav> getAllRecipes() {
        return recipeFavRepository.findAll();
    }

    @GetMapping("/recipes/{ingredient}")
    public List<RecipeFav> getRecipesByIngredient(@PathVariable String ingredient) {
        return recipeFavRepository.findByNameContaining(ingredient);
    }

    @PostMapping("/recipes/{idUser}")
    public RecipeFav createRecipe(@PathVariable Long idUser, @RequestBody RecipeFav recipeFav) {
        Optional<RecipeFav> recipe = recipeFavRepository.findByName(recipeFav.getName());
        User user = userRepository.findById(idUser).get();
        if (!(recipe.isPresent())) {
            RecipeFav recipeCreated = recipeFavRepository.save(recipeFav);
            user.getRecipeFavs().add(recipeCreated);
            userRepository.save(user);
            return recipeCreated;
        }
        RecipeFav recipeUpdated = recipe.get();
        recipeUpdated.setUsers(recipeFav.getUsers());
        recipeFavRepository.save(recipeUpdated);
        user.getRecipeFavs().add(recipeUpdated);
        userRepository.save(user);
        return recipe.get();
    }

    @DeleteMapping("/recipes/{idRecipe}/{idUser}")
    public RecipeFav deleteRecipe(@PathVariable Long idRecipe, @PathVariable Long idUser) {
        User userUpdate = userRepository.findById(idUser).get();
        Set<RecipeFav> listRecipe = userUpdate.getRecipeFavs();
        RecipeFav recipeToDelete = recipeFavRepository.findById(idRecipe).get();
        Optional<RecipeFav> recipeFavOptional = listRecipe.stream().filter(recipeFav -> recipeFav.getId() == recipeToDelete.getId()).findFirst();
        if (recipeFavOptional.isPresent()){
            userUpdate.getRecipeFavs().remove(recipeFavOptional.get());
            userRepository.save(userUpdate);
        }
        Set<User> listUser = recipeToDelete.getUsers();
        Optional<User> userOptional = listUser.stream().filter(user -> user.getId() == userUpdate.getId()).findFirst();
        if (userOptional.isPresent()){
            recipeToDelete.getUsers().remove(userOptional.get());
            recipeFavRepository.save(recipeToDelete);
        }
        if (recipeToDelete.getUsers().size() == 0) {
             recipeFavRepository.delete(recipeToDelete);
            return null;
        }
        return recipeFavRepository.save(recipeToDelete);
    }
}