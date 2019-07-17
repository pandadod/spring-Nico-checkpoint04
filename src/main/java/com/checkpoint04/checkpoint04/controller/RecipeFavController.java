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
    public void deleteRecipe(@PathVariable Long idRecipe, @PathVariable Long idUser) {
        User user = userRepository.findById(idUser).get();
        RecipeFav recipeToDelete = recipeFavRepository.findById(idRecipe).get();
        user.getRecipeFavs().remove(recipeToDelete);
        userRepository.save(user);
        Set<User> listUser = recipeToDelete.getUsers();
        listUser.remove(user);
        if (listUser.size() == 0) {
            recipeFavRepository.delete(recipeToDelete);
        }
        recipeFavRepository.save(recipeToDelete);
    }
}