package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.exception.NotFoundException;
import com.shumyk.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@AllArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;

	@GetMapping("recipe/{id}/show") public String getRecipe(@PathVariable final Long id, final Model model) {
		final Recipe recipe = recipeService.getRecipeById(id);
		model.addAttribute("recipe", recipe);

		log.debug("Returning recipe page for {} recipe.", recipe.getDescription());
		return "recipe/show";
	}

	@RequestMapping("recipe/new")
	public String newRecipe(final Model model) {
		model.addAttribute("recipe", new RecipeCommand());

		return "recipe/recipeForm";
	}

	@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable final Long id, final Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/recipeForm";
	}

	@RequestMapping("recipe/{id}/delete")
	public String deleteRecipe(@PathVariable final Long id) {
		recipeService.deleteById(id);
		return "redirect:/";
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute final RecipeCommand command) {
		final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		log.debug("Saved Recipe [{}]", savedCommand);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound() {
		log.error("Handling not found exception");
		final ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		return modelAndView;
	}
}
