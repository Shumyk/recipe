package com.shumyk.recipe.service;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

	Recipe getRecipeById(final Long id);

	Set<Recipe> getRecipes();

	RecipeCommand findCommandById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	void deleteById(Long id);
}
