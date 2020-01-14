package com.shumyk.recipe.bootstrap;

import com.shumyk.recipe.domain.*;
import com.shumyk.recipe.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static java.util.Collections.singleton;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;

@Component
public class DataLoader implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final NotesRepository notesRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientRepository ingredientRepository;

	public DataLoader(final CategoryRepository categoryRepository, final NotesRepository notesRepository,
										final RecipeRepository recipeRepository, final UnitOfMeasureRepository unitOfMeasureRepository,
										final IngredientRepository ingredientRepository) {
		this.categoryRepository = categoryRepository;
		this.notesRepository = notesRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		final Category mexicanCategory = categoryRepository.findByDescription("Mexican").get();

		final UnitOfMeasure uomPiece = unitOfMeasureRepository.findByDescription("Piece").get();
		final UnitOfMeasure uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
		final UnitOfMeasure uomTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
		final UnitOfMeasure uomDash = unitOfMeasureRepository.findByDescription("Dash").get();


		final Notes guacamoleNote = new Notes();
		guacamoleNote.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");

		final Ingredient ripeAvocado = new Ingredient();
		ripeAvocado.setAmount(BigDecimal.valueOf(2));
		ripeAvocado.setDescription("ripe avocados");
		ripeAvocado.setUom(uomPiece);

		final Ingredient salt = new Ingredient();
		salt.setAmount(BigDecimal.valueOf(0.25));
		salt.setDescription("redOnion");
		salt.setUom(uomTeaspoon);

		final Ingredient limeJuice = new Ingredient();
		limeJuice.setAmount(BigDecimal.valueOf(1));
		limeJuice.setDescription("fresh lime juice or lemon juice");
		limeJuice.setUom(uomTablespoon);

		final Ingredient redOnion = new Ingredient();
		redOnion.setAmount(BigDecimal.valueOf(2));
		redOnion.setDescription("tablespoons to 1/4 cup of minced red onion or thinly sliced green onion");
		redOnion.setUom(uomTablespoon);

		final Ingredient chiles = new Ingredient();
		chiles.setAmount(BigDecimal.valueOf(2));
		chiles.setDescription("serrano chiles, stems and seeds removed, minced");
		chiles.setUom(uomPiece);

		final Ingredient cilantro = new Ingredient();
		cilantro.setAmount(BigDecimal.valueOf(2));
		cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
		cilantro.setUom(uomTablespoon);

		final Ingredient blackPepper = new Ingredient();
		blackPepper.setAmount(BigDecimal.valueOf(1));
		blackPepper.setDescription("freshly grated black pepper");
		blackPepper.setUom(uomDash);

		final Ingredient ripeTomato = new Ingredient();
		ripeTomato.setAmount(BigDecimal.valueOf(0.5));
		ripeTomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
		ripeTomato.setUom(uomPiece);

		final Ingredient redRadishes = new Ingredient();
		redRadishes.setAmount(BigDecimal.valueOf(5));
		redRadishes.setDescription("Red radishes or jicama, to garnish");
		redRadishes.setUom(uomPiece);

		final Ingredient tortillaChips = new Ingredient();
		tortillaChips.setAmount(BigDecimal.valueOf(20));
		tortillaChips.setDescription("Tortilla chips, to serve");
		tortillaChips.setUom(uomPiece);


		final Recipe guacamoleRecipe = new Recipe();
		guacamoleRecipe.setDescription("Perfect Guacamole Recipe");
		guacamoleRecipe.setNotes(guacamoleNote);
		guacamoleRecipe.setPrepTime(10);
		guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		guacamoleRecipe.setImage(getImage("images/Guacamole.jpg"));
		guacamoleRecipe.setCategories(singleton(mexicanCategory));
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

		ripeAvocado.setRecipe(guacamoleRecipe);
		ingredientRepository.save(ripeAvocado);

		salt.setRecipe(guacamoleRecipe);
		ingredientRepository.save(salt);

		limeJuice.setRecipe(guacamoleRecipe);
		ingredientRepository.save(limeJuice);

		redOnion.setRecipe(guacamoleRecipe);
		ingredientRepository.save(redOnion);

		chiles.setRecipe(guacamoleRecipe);
		ingredientRepository.save(chiles);

		cilantro.setRecipe(guacamoleRecipe);
		ingredientRepository.save(cilantro);

		blackPepper.setRecipe(guacamoleRecipe);
		ingredientRepository.save(blackPepper);

		ripeTomato.setRecipe(guacamoleRecipe);
		ingredientRepository.save(ripeTomato);

		redRadishes.setRecipe(guacamoleRecipe);
		ingredientRepository.save(redRadishes);

		tortillaChips.setRecipe(guacamoleRecipe);
		ingredientRepository.save(tortillaChips);

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
