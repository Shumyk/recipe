package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.service.IngredientService;
import com.shumyk.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

	@Mock IngredientService ingredientService;
	@Mock RecipeService recipeService;
	IngredientController controller;
	MockMvc mockMvc;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(recipeService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test public void testListIngredient() throws Exception {
		// given
		RecipeCommand command = new RecipeCommand();
		doReturn(command).when(recipeService).findCommandById(anyLong());

		// when
		mockMvc.perform(get("/recipe/4/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));

		// then
		verify(recipeService).findCommandById(4L);
	}

	@Test public void testShowIngredient() throws Exception {
		IngredientCommand command = new IngredientCommand();

		doReturn(command).when(ingredientService).findByRecipeIdAndIngredientId(anyLong(), anyLong());

		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));
	}
}
