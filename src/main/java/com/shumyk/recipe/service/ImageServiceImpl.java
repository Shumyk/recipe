package com.shumyk.recipe.service;

import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	@Override
	public void saveImageFile(final Long recipeId, final MultipartFile file) {
		try {
			final Recipe recipe = recipeRepository.findById(recipeId).get();
			final byte[] byteObject = Arrays.copyOf(file.getBytes(), file.getBytes().length);
			recipe.setImage(byteObject);
			recipeRepository.save(recipe);
		} catch (IOException ex) {
			// TODO handle exception better
			log.error("Failed during saving image", ex);
		}
	}
}
