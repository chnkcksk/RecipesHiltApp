package com.chnkcksk.recipeshiltapp.model

data class RecipesResponse(
    val recipes: List<Recipe>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
