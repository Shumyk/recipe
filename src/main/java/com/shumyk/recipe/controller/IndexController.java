package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Category;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.domain.UnitOfMeasure;
import com.shumyk.recipe.repository.CategoryRepository;
import com.shumyk.recipe.repository.RecipeRepository;
import com.shumyk.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        final Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        final Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category Id is: " + categoryOptional.get().getId());
        System.out.println("UOM Id is: " + unitOfMeasureOptional.get().getId());

        return "index";
    }

    @GetMapping("/recipe/{id}") public String getGuacamoleRecipe(@PathVariable final Long id, final Model model) {
        final Recipe guacamoleRecipe = recipeRepository.findById(id).get();

        model.addAttribute("recipe", guacamoleRecipe);

        return "recipe";
    }
}
