package com.shumyk.recipe.controller;

import com.shumyk.recipe.service.ImageService;
import com.shumyk.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;

	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable final Long id, final Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/imageUploadForm";
	}

	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable final Long id, @RequestParam("imageFile") final MultipartFile file) {
		imageService.saveImageFile(id, file);
		return "redirect:/recipe/" + id + "/show";
	}
}
