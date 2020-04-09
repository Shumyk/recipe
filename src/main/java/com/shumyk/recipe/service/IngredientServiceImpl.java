package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.converter.IngredientCommandToIngredient;
import com.shumyk.recipe.converter.IngredientToIngredientCommand;
import com.shumyk.recipe.domain.Ingredient;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import com.shumyk.recipe.repository.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand toIngredientCommand;
	private final IngredientCommandToIngredient toIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

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

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(final IngredientCommand command) {
		final Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		if (recipeOptional.isEmpty()) {
			// TODO toss error if not found
			log.error("Recipe not found for ID: {}", command.getRecipeId());
			return new IngredientCommand();
		}
		final Recipe recipe = recipeOptional.get();
		final Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
			.filter(ingredient -> ingredient.getId().equals(command.getId()))
			.findFirst();

		if (ingredientOptional.isPresent()) {
			final Ingredient ingredientFound = ingredientOptional.get();
			ingredientFound.setDescription(command.getDescription());
			ingredientFound.setAmount(command.getAmount());
			ingredientFound.setUom(
				unitOfMeasureRepository.findById(command.getUom().getId())
					.orElseThrow(() -> new RuntimeException("Unit Of Meausure not found! ID: " + command.getUom().getId())) // TODO address this
			);
		} else {
			recipe.addIngredient(requireNonNull(toIngredient.convert(command)));
		}
		final Recipe savedRecipe = recipeRepository.save(recipe);
		return toIngredientCommand.convert(
			savedRecipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(command.getId()))
				.findFirst()
				.orElse(new Ingredient())
		);
	}
}
