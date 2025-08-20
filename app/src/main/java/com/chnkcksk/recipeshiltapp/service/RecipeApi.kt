package com.chnkcksk.recipeshiltapp.service

import com.chnkcksk.recipeshiltapp.model.Recipe
import com.chnkcksk.recipeshiltapp.model.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null
    ): RecipesResponse

    @GET("recipes/{id}")
    suspend fun getRecipeById(@Path("id") id: Int): Recipe

}