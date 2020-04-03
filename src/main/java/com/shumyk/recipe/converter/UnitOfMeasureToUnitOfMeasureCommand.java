package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (isNull(source)) {
			return null;
		}
		return UnitOfMeasureCommand.of(source.getId(), source.getDescription());
	}
}
