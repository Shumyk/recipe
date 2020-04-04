package com.shumyk.recipe.controller;

import com.shumyk.recipe.service.IngredientService;
import com.shumyk.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	@GetMapping("recipe/{id}/ingredients")
	public String ingredients(@PathVariable final Long id, final Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		log.debug("Returning list of ingredients for Recipe with ID: {}", id);
		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable final Long recipeId, @PathVariable final Long id, final Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}
}
