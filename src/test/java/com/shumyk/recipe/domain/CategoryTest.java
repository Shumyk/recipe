package com.shumyk.recipe.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

	private Category category;

	@Before public void setUp() {
		category = new Category();
	}

	@Test
	public void getId() {
		final Long idValue = 4L;
		category.setId(idValue);

		assertEquals(idValue, category.getId());
	}

	@Test
	public void getDescription() {
		final String description = "DUmmy DEscription";
		category.setDescription(description);

		assertEquals(description, category.getDescription());
	}

	@Test
	public void getRecipes() {
		final Set<Recipe> recipeSet = new HashSet<>();
		recipeSet.add(new Recipe());
		category.setRecipes(recipeSet);

		assertEquals(recipeSet, category.getRecipes());
	}
}