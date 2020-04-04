package com.shumyk.recipe.service;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.converter.RecipeCommandToRecipe;
import com.shumyk.recipe.converter.RecipeToRecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe toRecipe;
	private final RecipeToRecipeCommand toRecipeCommand;

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe getRecipeById(final Long id) {
		return recipeRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Recipe Not Found, id = " + id));
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(final Long id) {
		return toRecipeCommand.convert(getRecipeById(id));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(final RecipeCommand command) {
		final Recipe detachedRecipe = toRecipe.convert(command);

		assert detachedRecipe != null;
		final Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId: {}", savedRecipe.getId());
		return toRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public void deleteById(final Long id) {
		recipeRepository.deleteById(id);
		log.debug("Deleted Recipe with ID: {}", id);
	}
}

