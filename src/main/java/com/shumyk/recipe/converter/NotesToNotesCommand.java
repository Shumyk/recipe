package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.NotesCommand;
import com.shumyk.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		if (isNull(source)) {
			return null;
		}
		return NotesCommand.of(source.getId(), source.getRecipeNotes());
	}
}
