package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;

	@GetMapping("recipe/show/{id}") public String getRecipe(@PathVariable final Long id, final Model model) {
		final Recipe recipe = recipeService.getRecipeById(id).orElse(new Recipe());
		model.addAttribute("recipe", recipe);

		log.info("Returning recipe page for {} recipe.", recipe.getDescription());
		return "/recipe/show";
	}

	@RequestMapping("recipe/new")
	public String newRecipe(final Model model) {
		model.addAttribute("recipe", new RecipeCommand());

		return "/recipe/recipeForm";
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute final RecipeCommand command) {
		final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		return "redirect:/recipe/show/" + savedCommand.getId();
	}
}
