package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.RecipeCommand;
import com.shumyk.recipe.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Set.of;
import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final IngredientToIngredientCommand toIngredientCommand;
	private final NotesToNotesCommand toNotesCommand;
	private final CategoryToCategoryCommand toCategoryCommand;

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (isNull(source)) {
			return null;
		}

		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setIngredients(
			isNull(source.getIngredients()) ?
				of() : source.getIngredients().stream().map(toIngredientCommand::convert).collect(toSet())
		);
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setNotes(toNotesCommand.convert(source.getNotes()));
		recipeCommand.setCategories(
			isNull(source.getCategories()) ?
				of() : source.getCategories().stream().map(toCategoryCommand::convert).collect(toSet())
		);
		return recipeCommand;
	}
}
