package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.IngredientCommand;
import com.shumyk.recipe.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure commandToUnitOfMeasure;

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (isNull(source)) {
			return null;
		}
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setUom(commandToUnitOfMeasure.convert(source.getUom()));
		return ingredient;
	}
}
