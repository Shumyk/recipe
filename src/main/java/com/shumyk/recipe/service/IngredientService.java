package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
