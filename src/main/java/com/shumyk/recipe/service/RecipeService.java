package com.shumyk.recipe.service;

import com.shumyk.recipe.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

	Optional<Recipe> getRecipeById(final Long id);

	Set<Recipe> getRecipes();
}
