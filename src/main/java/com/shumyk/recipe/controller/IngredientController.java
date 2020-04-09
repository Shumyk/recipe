package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.service.IngredientService;
import com.shumyk.recipe.service.RecipeService;
import com.shumyk.recipe.service.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	@GetMapping("recipe/{recipeId}/ingredients")
	public String ingredients(@PathVariable final Long recipeId, final Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		log.debug("Returning list of ingredients for Recipe with ID: {}", recipeId);
		return "recipe/ingredient/list";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable final Long recipeId, @PathVariable final Long id, final Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}

	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable final Long recipeId, final Model model) {
		// make sure we have a good id value
		final RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
		// TODO raise exception if null

		// need to return back parent id for hidden form property
		final IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		model.addAttribute("ingredient", ingredientCommand);

		// init uom
		ingredientCommand.setUom(new UnitOfMeasureCommand());

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

		return "recipe/ingredient/ingredientForm";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable final Long recipeId, @PathVariable final Long id, final Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		return "recipe/ingredient/ingredientForm";
	}

	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteRecipeIngredient(@PathVariable final Long recipeId, @PathVariable final Long id) {
		ingredientService.deleteIngredientById(recipeId, id);
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute final IngredientCommand command) {
		final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

		log.debug("Saved Ingredient ID: {}", savedCommand.getId());
		log.debug("Saved Recipe ID: {}", savedCommand.getRecipeId());

		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
}
