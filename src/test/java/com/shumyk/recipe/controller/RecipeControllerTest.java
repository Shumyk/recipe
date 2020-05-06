package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.exception.NotFoundException;
import com.shumyk.recipe.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

	private RecipeController recipeController;
	@Mock private RecipeService recipeService;
	@Mock private Model model;

	MockMvc mockMvc;

	@Before	public void setUp() {
		MockitoAnnotations.initMocks(this);

		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test public void testGetRecipe() throws Exception {
		final Recipe recipe = new Recipe();
		recipe.setId(50L);
		recipe.setDescription("Fun and chill");

		doReturn(recipe).when(recipeService).getRecipeById(anyLong());

		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test public void testGetRecipeNotFound() throws Exception {
		when(recipeService.getRecipeById(1L)).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isNotFound())
			.andExpect(view().name("404error"));
	}

	@Test public void testGetRecipeNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/yolo/show"))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("400error"));
	}

	@Test public void getRecipe() {
		final Long id = 148L;
		final Recipe recipe = new Recipe();

		when(recipeService.getRecipeById(id)).thenReturn(recipe);

		assertEquals("recipe/show", recipeController.getRecipe(id, model));

		verify(recipeService, times(1)).getRecipeById(id);
		verify(model, times(1)).addAttribute("recipe", recipe);
	}

	@Test public void testPostNewRecipeForm() throws Exception {
		final RecipeCommand command = new RecipeCommand();
		command.setId(6L);

		doReturn(command).when(recipeService).saveRecipeCommand(any(RecipeCommand.class));

		mockMvc.perform(
			post("/recipe")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("id", "")
			.param("description", "valuable message")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/6/show"));

	}

	@Test public void testGetUpdateView() throws Exception {
		final RecipeCommand command = new RecipeCommand();
		command.setId(9L);

		doReturn(command).when(recipeService).findCommandById(anyLong());

		mockMvc.perform(get("/recipe/1/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeForm"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test public void testDeleteAction() throws Exception {
		mockMvc.perform(get("/recipe/2/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));

		verify(recipeService).deleteById(2L);
	}
}
