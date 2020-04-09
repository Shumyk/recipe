package com.shumyk.recipe.service;

import com.shumyk.recipe.command.UnitOfMeasureCommand;
import com.shumyk.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import com.shumyk.recipe.repository.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
			.map(toUnitOfMeasureCommand::convert)
			.collect(Collectors.toSet());

	}
}
