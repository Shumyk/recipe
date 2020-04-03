package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.CategoryCommand;
import com.shumyk.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand categoryCommand) {
		if (isNull(categoryCommand)) {
			return null;
		}

		final Category category = new Category();
		category.setId(categoryCommand.getId());
		category.setDescription(categoryCommand.getDescription());
		return category;
	}
}
