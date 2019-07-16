package com.checkpoint04.checkpoint04.controller;

import com.checkpoint04.checkpoint04.entity.RecipeFav;
import com.checkpoint04.checkpoint04.repository.RecipeFavRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeFavController {

    @Autowired
    RecipeFavRepository recipeFavRepository;

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
        return recipeFavRepository.save(recipeFav);
    }

    @PutMapping("/recipes/{id}")
    public RecipeFav update(@PathVariable Long id,
                            @RequestBody RecipeFav recipeFavUpdate) {
        RecipeFav recipeFavFromDB = recipeFavRepository.findById(id).get();
        if (recipeFavUpdate.getName() != null && !recipeFavUpdate.getName().isEmpty()) {
            recipeFavFromDB.setName(recipeFavUpdate.getName());
        }
        if (recipeFavUpdate.getImageUrl() != null) {
            recipeFavFromDB.setImageUrl(recipeFavUpdate.getImageUrl());
        }
        if (recipeFavUpdate.getSourceUrl() != null) {
            recipeFavFromDB.setSourceUrl(recipeFavUpdate.getSourceUrl());
        }

        return recipeFavRepository.save(recipeFavFromDB);
    }
}