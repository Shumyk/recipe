package com.shumyk.recipe.controller;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.service.ImageService;
import com.shumyk.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

	@Mock ImageService imageService;
	@Mock RecipeService recipeService;
	ImageController imageController;
	MockMvc mockMvc;

	@BeforeEach void setUp() {
		MockitoAnnotations.initMocks(this);

		imageController = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	@Test void getImageForm() throws Exception {
		// given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);

		doReturn(command).when(recipeService).findCommandById(1L);

		// when
		mockMvc.perform(get("/recipe/1/image"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"));
		verify(recipeService).findCommandById(1L);
	}

	@Test void handleImagePost() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "testing.txt", "text/plain", "Shumyk's recipe".getBytes());
		this.mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/recipe/1/show"));
		verify(imageService).saveImageFile(1L, multipartFile);
	}
}