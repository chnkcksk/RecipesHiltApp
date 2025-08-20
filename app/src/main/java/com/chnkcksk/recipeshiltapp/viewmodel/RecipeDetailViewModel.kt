package com.chnkcksk.recipeshiltapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chnkcksk.recipeshiltapp.model.Recipe
import com.chnkcksk.recipeshiltapp.repository.RecipeRepository
import com.chnkcksk.recipeshiltapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    var recipe = mutableStateOf<Recipe?>(null)

    var errorMessage = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    fun loadRecipeById(id: Int) {

        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getRecipeById(id)

            when(result){
                is Resource.Success -> {

                    recipe.value = result.data
                    println("result.data: ${result.data}")
                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error ->{
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading ->{

                }

            }
        }
    }

}