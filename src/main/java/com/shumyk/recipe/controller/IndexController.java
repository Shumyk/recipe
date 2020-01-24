package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@AllArgsConstructor
public class IndexController {

    @NonNull private final RecipeService recipeService;

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(final Model model) {
        log.info("Generating Index page.");

        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @GetMapping("/recipe/{id}") public String getGuacamoleRecipe(@PathVariable final Long id, final Model model) {
        final Recipe guacamoleRecipe = recipeService.getRecipeById(id)
                .orElse(new Recipe());
        model.addAttribute("recipe", guacamoleRecipe);

        log.info("Returning recipe page for {} recipe.", guacamoleRecipe.getDescription());
        return "recipe";
    }
}
