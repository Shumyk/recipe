package com.shumyk.recipe.controller;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

	public static final String INDEX = "index";
	public static final String RECIPES = "recipes";

	private IndexController indexController;
	@Mock private RecipeService recipeService;
	@Mock private Model model;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		indexController = new IndexController(recipeService);
	}

	@Test public void testMockMvc() throws Exception {
		final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name(INDEX));
	}

	@Test public void getIndexPage() {
		// given
		final Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		recipes.add(Recipe.builder().id(40L).build());
		recipes.add(Recipe.builder().id(60L).build());

		when(recipeService.getRecipes()).thenReturn(recipes);

		final ArgumentCaptor<Set<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

		// when
		final String viewName = indexController.getIndexPage(model);

		// then
		assertEquals(INDEX, viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq(RECIPES), setArgumentCaptor.capture());

		final Set<Recipe> setInController = setArgumentCaptor.getValue();
		assertEquals(3, setInController.size());
	}
}