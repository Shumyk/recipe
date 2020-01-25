package com.shumyk.recipe.service;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeService;

	@Mock private RecipeRepository recipeRepository;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void getRecipes() {
		final Recipe recipe = new Recipe();
		final Set<Recipe> recipeSet = new HashSet();
		recipeSet.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipeSet);

		final Set<Recipe> recipes = recipeService.getRecipes();
		assertEquals(1, recipes.size());

		verify(recipeRepository, times(1)).findAll();
	}
}