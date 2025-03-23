// Recipe.kt
package com.example.recipesearchapp

data class RecipeResponse(
    val hits: List<RecipeHit>
)

data class RecipeHit(
    val recipe: Recipe
)

data class Recipe(
    val label: String,
    val image: String,
    val source: String
)
