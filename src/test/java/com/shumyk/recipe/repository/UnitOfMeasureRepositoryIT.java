package com.shumyk.recipe.repository;

import com.shumyk.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

	@Autowired private UnitOfMeasureRepository unitOfMeasureRepository;

	@Before	public void setUp() throws Exception {
	}

	@Test	public void findByDescriptionTeaspoon() {
		Optional<UnitOfMeasure> uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

		assertEquals("Teaspoon", uomTeaspoon.get().getDescription());
	}

	@Test	public void findByDescriptionCup() {
		Optional<UnitOfMeasure> uomTeaspoon = unitOfMeasureRepository.findByDescription("Cup");

		assertEquals("Cup", uomTeaspoon.get().getDescription());
	}
}