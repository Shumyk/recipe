package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

	public static final String INDEX = "index";
	public static final String RECIPE = "recipe";
	public static final String RECIPES = "recipes";

	private IndexController indexController;
	@Mock private RecipeService recipeService;
	@Mock private Model model;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		indexController = new IndexController(recipeService);
	}

	@Test
	public void getIndexPage() {
		final Recipe recipe = new Recipe();
		final Set<Recipe> recipes = new HashSet<>();
		recipes.add(recipe);

		when(recipeService.getRecipes()).thenReturn(recipes);

		assertEquals(INDEX, indexController.getIndexPage(model));

		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(RECIPES, recipes);
	}

	@Test
	public void getRecipe() {
		final Long id = 148L;
		final Optional<Recipe> recipe = Optional.of(new Recipe());

		when(recipeService.getRecipeById(id)).thenReturn(recipe);

		assertEquals(RECIPE, indexController.getRecipe(id, model));

		verify(recipeService, times(1)).getRecipeById(id);
		verify(model, times(1)).addAttribute(RECIPE, recipe.get());
	}
}