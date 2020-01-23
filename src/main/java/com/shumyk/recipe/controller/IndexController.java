package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class IndexController {

    @NonNull private final RecipeRepository recipeRepository;

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        log.info("Generating Index page.");
        return "index";
    }

    @GetMapping("/recipe/{id}") public String getGuacamoleRecipe(@PathVariable final Long id, final Model model) {
        final Recipe guacamoleRecipe = recipeRepository.findById(id).get();
        model.addAttribute("recipe", guacamoleRecipe);

        log.info("Returning recipe page for {} recipe.", guacamoleRecipe.getDescription());
        return "recipe";
    }
}
