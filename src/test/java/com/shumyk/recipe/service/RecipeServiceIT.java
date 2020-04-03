package com.shumyk.recipe.service;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.converter.RecipeCommandToRecipe;
import com.shumyk.recipe.converter.RecipeToRecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import com.shumyk.recipe.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New Description";

	@Autowired private RecipeService recipeService;
	@Autowired private RecipeRepository recipeRepository;
	@Autowired private RecipeCommandToRecipe toRecipe;
	@Autowired private RecipeToRecipeCommand toRecipeCommand;

	@Transactional @Test public void testSaveNewDescription() {
		// given
		final Recipe testRecipe = recipeRepository.findAll().iterator().next();
		final RecipeCommand testRecipeCommand = toRecipeCommand.convert(testRecipe);

		// when
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		final RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

		// then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
	}
}
