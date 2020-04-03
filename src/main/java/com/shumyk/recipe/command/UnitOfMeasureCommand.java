package com.shumyk.recipe.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UnitOfMeasureCommand {
	private Long id;
	private String description;
}
