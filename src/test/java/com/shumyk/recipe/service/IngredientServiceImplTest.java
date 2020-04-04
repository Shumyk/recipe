package com.shumyk.recipe.service;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.converter.IngredientToIngredientCommand;
import com.shumyk.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.shumyk.recipe.domain.Ingredient;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	@Mock RecipeRepository recipeRepository;
	IngredientService ingredientService;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
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
}