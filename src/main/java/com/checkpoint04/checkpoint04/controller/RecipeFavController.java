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

    @PostMapping("/recipes")
    public RecipeFav createRecipe(@RequestBody RecipeFav recipeFav) {
        Optional<RecipeFav> recipe = recipeFavRepository.findByName(recipeFav.getName());
        if (!(recipe.isPresent())) {
            return recipeFavRepository.save(recipeFav);
        }
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
        recipeFavRepository.save(recipeToDelete);
        if (listUser.size() == 0) {
            recipeFavRepository.delete(recipeToDelete);
        }

    }
}