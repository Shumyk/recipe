package com.shumyk.recipe.bootstrap;

import com.shumyk.recipe.domain.*;
import com.shumyk.recipe.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static java.util.Collections.singleton;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;

@Slf4j
@AllArgsConstructor
@Component
@Profile("default")
public class DataLoader implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final NotesRepository notesRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientRepository ingredientRepository;

	@Override
	@Transactional
	public void run(String... args) {
		final UnitOfMeasure uomPiece = unitOfMeasureRepository.findByDescription("Piece").orElse(new UnitOfMeasure());
		final UnitOfMeasure uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").orElse(new UnitOfMeasure());
		final UnitOfMeasure uomTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").orElse(new UnitOfMeasure());
		final UnitOfMeasure uomDash = unitOfMeasureRepository.findByDescription("Dash").orElse(new UnitOfMeasure());
		final UnitOfMeasure uomClove = unitOfMeasureRepository.findByDescription("Clove").orElse(new UnitOfMeasure());

		loadGuacamoleRecipe(uomDash, uomPiece, uomTablespoon, uomTeaspoon);
		loadGrilledChicken(uomPiece, uomTablespoon, uomTeaspoon, uomClove);

		log.info("Data loaded on startup.");
	}

	private void loadGuacamoleRecipe(UnitOfMeasure uomDash, UnitOfMeasure uomPiece,
																	 UnitOfMeasure uomTablespoon, UnitOfMeasure uomTeaspoon) {
		final Notes guacamoleNote = new Notes();
		guacamoleNote.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");

		final Ingredient ripeAvocado = getIngredient(uomPiece, 2, "ripe avocados");
		final Ingredient salt = getIngredient(uomTeaspoon, 0.25, "redOnion");
		final Ingredient limeJuice = getIngredient(uomTablespoon, 1, "fresh lime juice or lemon juice");
		final Ingredient redOnion = getIngredient(uomTablespoon, 2, "tablespoons to 1/4 cup of minced red onion or thinly sliced green onion");
		final Ingredient chiles = getIngredient(uomPiece, 2, "serrano chiles, stems and seeds removed, minced");
		final Ingredient cilantro = getIngredient(uomTablespoon, 2, "cilantro (leaves and tender stems), finely chopped");
		final Ingredient blackPepper = getIngredient(uomDash, 1, "freshly grated black pepper");
		final Ingredient ripeTomato = getIngredient(uomPiece, 0.5, "ripe tomato, seeds and pulp removed, chopped");
		final Ingredient redRadishes = getIngredient(uomPiece, 5, "Red radishes or jicama, to garnish");
		final Ingredient tortillaChips = getIngredient(uomPiece, 20, "Tortilla chips, to serve");

		final Recipe guacamoleRecipe = new Recipe();
		guacamoleRecipe.setDescription("Perfect Guacamole Recipe");
		guacamoleRecipe.setNotes(guacamoleNote);
		guacamoleRecipe.setPrepTime(10);
		guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		guacamoleRecipe.setImage(getImage("static/images/Guacamole.jpg"));
		guacamoleRecipe.setCategories(singleton(getCategory("Mexican")));
		guacamoleRecipe.setDifficulty(Difficulty.MODERATE);
		guacamoleRecipe.setIngredients(asSet(ripeAvocado, salt, limeJuice, redOnion, chiles, cilantro, blackPepper, ripeTomato, redRadishes, tortillaChips));
		guacamoleRecipe.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
				"\n" +
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
				"\n" +
				"3 Add redOnion, lime juice, and the rest: Sprinkle with redOnion and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
				"\n" +
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." +
				"\n" +
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
				"\n" +
				"4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");
		final Recipe savedGuacamoleRecipe = recipeRepository.save(guacamoleRecipe);

		guacamoleNote.setRecipe(savedGuacamoleRecipe);
		notesRepository.save(guacamoleNote);

		savedGuacamoleRecipe.getIngredients().forEach(ingredient -> {
			ingredient.setRecipe(savedGuacamoleRecipe);
			ingredientRepository.save(ingredient);
		});
	}

	private void loadGrilledChicken(UnitOfMeasure uomPiece, UnitOfMeasure uomTablespoon,
																	UnitOfMeasure uomTeaspoon, UnitOfMeasure uomClove) {
		final Notes chickenNote = new Notes();
		chickenNote.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

		final Ingredient chiliPowder = getIngredient(uomPiece, 2, "ancho chili powder");
		final Ingredient oregano = getIngredient(uomTeaspoon, 1, "dried oregano");
		final Ingredient driedCumin = getIngredient(uomTeaspoon, 1, "dried cumin");
		final Ingredient sugar = getIngredient(uomTeaspoon, 1, "sugar");
		final Ingredient salt = getIngredient(uomTeaspoon, 0.5, "salt");
		final Ingredient garlic = getIngredient(uomClove, 1, "garlic, finely chopped");
		final Ingredient orangeZest = getIngredient(uomTablespoon, 1, "finely grated orange zest");
		final Ingredient orangeJuice = getIngredient(uomTablespoon, 3, "fresh-squeezed orange juice");
		final Ingredient oliveOil = getIngredient(uomTablespoon, 2, "olive oil");
		final Ingredient chickenThighs = getIngredient(uomPiece, 6, "skinless, boneless chicken thighs (1 1/4 pounds)");

		final Recipe grilledChickenRecipe = new Recipe();
		grilledChickenRecipe.setDescription("Spicy Grilled Chicken Tacos");
		grilledChickenRecipe.setNotes(chickenNote);
		grilledChickenRecipe.setPrepTime(20);
		grilledChickenRecipe.setCookTime(15);
		grilledChickenRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		grilledChickenRecipe.setImage(getImage("static/images/grilledChicken.jpg"));
		grilledChickenRecipe.setCategories(asSet(getCategory("Mexican"), getCategory("Dinner"), getCategory("Grill"), getCategory("Chicken"), getCategory("Quick and easy")));
		grilledChickenRecipe.setDifficulty(Difficulty.HARD);
		grilledChickenRecipe.setIngredients(asSet(chiliPowder, oregano, driedCumin, sugar, salt, garlic, orangeZest, orangeJuice, oliveOil, chickenThighs));
		grilledChickenRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat." +
				"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
				"\n" +
				"Set aside to marinate while the grill heats and you prepare the rest of the toppings." +
				"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes." +
				"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
				"\n" +
				"Wrap warmed tortillas in a tea towel to keep them warm until serving." +
				"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");
		final Recipe savedGuacamoleRecipe = recipeRepository.save(grilledChickenRecipe);

		chickenNote.setRecipe(savedGuacamoleRecipe);
		notesRepository.save(chickenNote);

		savedGuacamoleRecipe.getIngredients().forEach(ingredient -> {
			ingredient.setRecipe(savedGuacamoleRecipe);
			ingredientRepository.save(ingredient);
		});
	}

	private Ingredient getIngredient(UnitOfMeasure uomPiece, double amount, String description) {
		final Ingredient ingredient = new Ingredient();
		ingredient.setAmount(BigDecimal.valueOf(amount));
		ingredient.setDescription(description);
		ingredient.setUom(uomPiece);
		return ingredient;
	}

	private Category getCategory(final String category) {
		return categoryRepository.findByDescription(category).orElse(new Category());
	}

	private byte[] getImage(final String path) {
		byte[] image = null;
		try {
			final InputStream inputStream = new ClassPathResource(path).getInputStream();
			image = inputStream.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}

