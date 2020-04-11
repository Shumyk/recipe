package com.shumyk.recipe.service;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class ImageServiceImplTest {

	private ImageService imageService;
	@Mock private RecipeRepository recipeRepository;

	@BeforeEach	void setUp() {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test void saveImageFile() throws IOException {
		// given
		final Long id = 1L;
		final MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Shumyk's recipe".getBytes());
		final Recipe recipe = new Recipe();
		recipe.setId(id);

		doReturn(Optional.of(recipe)).when(recipeRepository).findById(id);

		final ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		// when
		imageService.saveImageFile(id, multipartFile);

		// then
		verify(recipeRepository).save(argumentCaptor.capture());
		final Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}
}