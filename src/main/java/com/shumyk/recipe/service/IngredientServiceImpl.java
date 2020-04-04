package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.converter.IngredientToIngredientCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand toIngredientCommand;
	private final RecipeRepository recipeRepository;

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		final Recipe recipe = recipeRepository.findById(recipeId)
			.orElseThrow(() -> new NoSuchElementException("Not found Recipe with Id: " + recipeId));
		return recipe.getIngredients().stream()
			.filter(ingredient -> ingredient.getId().equals(ingredientId))
			.map(toIngredientCommand::convert)
			.findFirst()
			.orElseThrow(() -> new NoSuchElementException("Not found Ingredient with ID: " + ingredientId));
	}
}
