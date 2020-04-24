package com.shumyk.recipe.service;

import com.shumyk.recipe.converter.*;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.exception.NotFoundException;
import com.shumyk.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeService;

	@Mock private RecipeRepository recipeRepository;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(
			recipeRepository,
			new RecipeCommandToRecipe(
				new CategoryCommandToCategory(),
				new NotesCommandToNotes(),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())
			),
			new RecipeToRecipeCommand(
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NotesToNotesCommand(),
				new CategoryToCategoryCommand()
			)
		);
	}

	@Test public void getRecipes() {
		final Recipe recipe = new Recipe();
		final Set<Recipe> recipeSet = new HashSet();
		recipeSet.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipeSet);

		final Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(1, recipes.size());

		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyLong());
	}

	@Test public void deleteById() {
		// given
		final Long idToDelete = 2L;

		// when
		recipeService.deleteById(idToDelete);

		// then
		verify(recipeRepository).deleteById(idToDelete);
	}

	@Test(expected = NotFoundException.class) public void getRecipeByIdNotFound() {
		Optional<Recipe> recipeOptional = Optional.empty();
		when(recipeRepository.findById(1L)).thenReturn(recipeOptional);

		recipeService.getRecipeById(1L); // should go boom
	}
}