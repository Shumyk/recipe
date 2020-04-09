package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	void deleteIngredientById(Long recipeId, Long ingredientId);
}
