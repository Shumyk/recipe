package com.shumyk.recipe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	@Override
	public void saveImageFile(final Long recipeId, final MultipartFile file) {
		log.debug("Received a file");
	}
}
