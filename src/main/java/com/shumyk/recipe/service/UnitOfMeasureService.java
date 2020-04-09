package com.shumyk.recipe.service;

import com.shumyk.recipe.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
}
