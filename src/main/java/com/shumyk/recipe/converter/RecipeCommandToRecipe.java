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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final CategoryCommandToCategory toCategory;
	private final NotesCommandToNotes toNotes;
	private final IngredientCommandToIngredient toIngredient;

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (isNull(source)) {
			return null;
		}

		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setIngredients(
			isNull(source.getIngredients()) ?
				of() : source.getIngredients().stream().map(toIngredient::convert).collect(toSet())
		);
		recipe.setDifficulty(source.getDifficulty());
		recipe.setNotes(toNotes.convert(source.getNotes()));
		recipe.setCategories(
			isNull(source.getCategories()) ?
				of() : source.getCategories().stream().map(toCategory::convert).collect(toSet())
		);
		return recipe;
	}
}
