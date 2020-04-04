package com.shumyk.recipe.repository;

import com.shumyk.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Optional<Recipe> findByDescription(final String description);
}
