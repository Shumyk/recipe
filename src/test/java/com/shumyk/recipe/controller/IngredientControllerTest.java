package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.service.IngredientService;
import com.shumyk.recipe.service.RecipeService;
import com.shumyk.recipe.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

	@Mock IngredientService ingredientService;
	@Mock RecipeService recipeService;
	@Mock UnitOfMeasureService unitOfMeasureService;
	IngredientController controller;
	MockMvc mockMvc;

	@Before public void setUp() {
		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
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

	@Test public void testNewInfredientForm() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		// when
		doReturn(recipeCommand).when(recipeService).findCommandById(anyLong());
		doReturn(new HashSet<>()).when(unitOfMeasureService).listAllUoms();

		// then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientForm"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));

		verify(recipeService).findCommandById(anyLong());
	}

	@Test public void testUpdateIngredientForm() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		doReturn(ingredientCommand).when(ingredientService).findByRecipeIdAndIngredientId(anyLong(), anyLong());
		doReturn(new HashSet<>()).when(unitOfMeasureService).listAllUoms();

		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientForm"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
	}

	@Test public void testSaveOrUpdate() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		// when
		doReturn(command).when(ingredientService).saveIngredientCommand(any());

		// then
		mockMvc.perform(
			post("/recipe/2/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "3")
				.param("description", "wow that is great")
		)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}
}
