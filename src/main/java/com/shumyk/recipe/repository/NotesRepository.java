package com.shumyk.recipe.repository;

import com.shumyk.recipe.domain.Notes;
import com.shumyk.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotesRepository extends CrudRepository<Notes, Long> {

	Optional<Notes> findByRecipe(final Recipe recipe);
}
