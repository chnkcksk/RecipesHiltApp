package com.chnkcksk.recipeshiltapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnkcksk.recipeshiltapp.model.Recipe
import com.chnkcksk.recipeshiltapp.repository.RecipeRepository
import com.chnkcksk.recipeshiltapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    var recipeList = mutableStateOf<List<Recipe>>(listOf())

    var errorMessage = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    private var initialRecipeList = listOf<Recipe>()
    private var isSearchStarting = true

    init {
        loadRecipes()
    }

    fun searchRecipeList(query: String) {
        val listToSearch = if (isSearchStarting) {
            recipeList.value
        } else {
            initialRecipeList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (query.isEmpty()) {
                recipeList.value = initialRecipeList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter { recipe ->
                recipe.name.contains(
                    query.trim(),
                    ignoreCase = true
                ) || recipe.ingredients.any { ingredient ->
                    ingredient.contains(
                        query.trim(),
                        ignoreCase = true
                    )
                }
            }

            if (isSearchStarting == true) {
                initialRecipeList = recipeList.value
                isSearchStarting = false
            }

            recipeList.value = results

        }

    }


    fun loadRecipes() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getRecipeList()

            when (result) {
                is Resource.Success -> {

                    recipeList.value = result.data!!
                    println("result.data: ${result.data}")
                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {

                }

            }

        }
    }

}