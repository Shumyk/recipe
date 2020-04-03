package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	public static final String DESCRIPTION = "custom description";
	public static final Long ID_VALUE = 44L;

	final UnitOfMeasureCommandToUnitOfMeasure conventer = new UnitOfMeasureCommandToUnitOfMeasure();

	@Test void testNullParameter() {
		assertNull(conventer.convert(null));
	}

	@Test void testEmptyObject() {
		assertNotNull(conventer.convert(new UnitOfMeasureCommand()));
	}

	@Test void convert() {
		// given
		final UnitOfMeasureCommand uomCommand = UnitOfMeasureCommand.of(ID_VALUE, DESCRIPTION);

		// when
		final UnitOfMeasure uom = conventer.convert(uomCommand);

		// then
		assertNotNull(uom);
		assertEquals(ID_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
	}
}