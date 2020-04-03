package com.shumyk.recipe.converter;

import com.shumyk.recipe.command.CategoryCommand;
import com.shumyk.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {
		if (isNull(source)) {
			return null;
		}
		return CategoryCommand.of(source.getId(), source.getDescription());
	}
}
