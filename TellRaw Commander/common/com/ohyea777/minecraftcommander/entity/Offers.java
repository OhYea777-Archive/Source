package com.ohyea777.minecraftcommander.entity;

import java.util.ArrayList;

public class Offers {

	protected ArrayList<Recipe> Recipes;
	
	private void checkRecipes() {
		if (Recipes == null)
			Recipes = new ArrayList<Recipe>();
	}
	
	public void addRecipe(Recipe recipe) {
		checkRecipes();
		
		Recipes.add(recipe);
	}
	
}
