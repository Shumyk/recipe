package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
		if (isNull(unitOfMeasureCommand)) {
			return null;
		}

		final UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(unitOfMeasureCommand.getId());
		uom.setDescription(unitOfMeasureCommand.getDescription());
		return uom;
	}
}
