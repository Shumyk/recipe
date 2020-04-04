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
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToCommand;

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (isNull(source)) {
			return null;
		}

		return IngredientCommand.of(
			source.getId(),
			isNull(source.getRecipe()) ? null : source.getRecipe().getId(),
			source.getDescription(),
			source.getAmount(),
			unitOfMeasureToCommand.convert(source.getUom())
		);
	}
}
