package com.chnkcksk.recipeshiltapp.repository

import com.chnkcksk.recipeshiltapp.model.Recipe
import com.chnkcksk.recipeshiltapp.service.RecipeApi
import com.chnkcksk.recipeshiltapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class RecipeRepository @Inject constructor(
    private val api: RecipeApi
) {

    suspend fun getRecipeList(): Resource<List<Recipe>> {
        val response = try {
            val apiResponse = api.getRecipes()

            apiResponse
        } catch (e: Exception) {
            println("Repository Exception: ${e.message}") // Bu satırı ekleyin
            return Resource.Error("Error: ${e.message}")
        }

        return Resource.Success(response.recipes)
    }

    suspend fun getRecipeById(id: Int): Resource<Recipe> {
        val response = try {
            val recipe = api.getRecipeById(id)
            recipe
        } catch (e: Exception) {
            return Resource.Error("Error: ${e.message}")
        }

        return Resource.Success(response)
    }

}