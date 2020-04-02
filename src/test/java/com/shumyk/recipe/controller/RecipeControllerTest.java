package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeControllerTest {

	public static final String RECIPE = "recipe";

	private RecipeController recipeController;
	@Mock private RecipeService recipeService;
	@Mock private Model model;

	@Before	public void setUp() {
		MockitoAnnotations.initMocks(this);

		recipeController = new RecipeController(recipeService);
	}

	@Test	public void getRecipe() {
		final Long id = 148L;
		final Optional<Recipe> recipe = Optional.of(new Recipe());

		when(recipeService.getRecipeById(id)).thenReturn(recipe);

		assertEquals(RECIPE, recipeController.getRecipe(id, model));

		verify(recipeService, times(1)).getRecipeById(id);
		verify(model, times(1)).addAttribute(RECIPE, recipe.get());
	}
}
