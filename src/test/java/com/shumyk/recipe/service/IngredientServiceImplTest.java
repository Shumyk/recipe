package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.converter.IngredientCommandToIngredient;
import com.shumyk.recipe.converter.IngredientToIngredientCommand;
import com.shumyk.recipe.converter.UnitOfMeasureCommandToUnitOfMeasure;
import com.shumyk.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.shumyk.recipe.domain.Ingredient;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import com.shumyk.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	private final IngredientCommandToIngredient toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	@Mock RecipeRepository recipeRepository;
	@Mock UnitOfMeasureRepository unitOfMeasureRepository;
	IngredientService ingredientService;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(toIngredientCommand, toIngredient, recipeRepository, unitOfMeasureRepository);
	}

	@Test public void findByRecipeIdAndIngredientId() {
		// given
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		doReturn(Optional.of(recipe)).when(recipeRepository).findById(anyLong());

		// when
		IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		// then
		assertEquals(3L, command.getId());
		assertEquals(1L, command.getRecipeId());
		verify(recipeRepository).findById(1L);
	}

	@Test public void testSaveRecipeCommand() {
		// given
		final IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		final Optional<Recipe> recipeOptional = Optional.of(new Recipe());

		final Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);

		doReturn(recipeOptional).when(recipeRepository).findById(anyLong());
		doReturn(savedRecipe).when(recipeRepository).save(any());

		// when
		final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

		// then
		assertEquals(3L, savedCommand.getId());
		verify(recipeRepository).findById(anyLong());
		verify(recipeRepository).save(any(Recipe.class));
	}

	@Test public void testDeleteById() {
		// given
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);

		doReturn(Optional.of(recipe)).when(recipeRepository).findById(anyLong());

		// when
		ingredientService.deleteIngredientById(1L, 3L);

		// then
		verify(recipeRepository).findById(1L);
		verify(recipeRepository).save(recipe);
	}
}