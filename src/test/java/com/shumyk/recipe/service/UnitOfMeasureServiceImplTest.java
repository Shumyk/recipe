package com.shumyk.recipe.service;

import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.shumyk.recipe.domain.UnitOfMeasure;
import com.shumyk.recipe.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class UnitOfMeasureServiceImplTest {

	private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	private UnitOfMeasureService service;

	@Mock private UnitOfMeasureRepository repository;

	@BeforeEach	void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new UnitOfMeasureServiceImpl(repository, toUnitOfMeasureCommand);
	}

	@Test void listAllUoms() {
		// given
		final Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		unitOfMeasures.add(UnitOfMeasure.of(1L, "first uom"));
		unitOfMeasures.add(UnitOfMeasure.of(2L, "second uom"));

		doReturn(unitOfMeasures).when(repository).findAll();

		// when
		final Set<UnitOfMeasureCommand> commands = service.listAllUoms();

		// then
		assertEquals(2, commands.size());
		verify(repository).findAll();
	}
}