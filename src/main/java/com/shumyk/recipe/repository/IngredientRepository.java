package com.shumyk.recipe.repository;

import com.shumyk.recipe.domain.Ingredient;
import com.shumyk.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

	public List<Ingredient> getByRecipe(final Recipe recipe);
}
