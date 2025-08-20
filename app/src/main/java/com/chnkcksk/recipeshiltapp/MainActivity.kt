package com.chnkcksk.recipeshiltapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chnkcksk.recipeshiltapp.ui.theme.RecipesHiltAppTheme
import com.chnkcksk.recipeshiltapp.view.RecipeDetailScreen
import com.chnkcksk.recipeshiltapp.view.RecipeListScreen
import com.chnkcksk.recipeshiltapp.viewmodel.RecipeDetailViewModel
import com.chnkcksk.recipeshiltapp.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val listViewModel: RecipeListViewModel by viewModels()
    private val detailViewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipesHiltAppTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "recipe_list_screen")
                {

                    composable(route = "recipe_list_screen") {
                        RecipeListScreen(
                            navController = navController,
                            viewModel = listViewModel
                        )
                    }

                    composable(route = "recipe_detail_screen/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {

                        val recipeId = remember {
                            it.arguments?.getInt("id")
                        }

                        RecipeDetailScreen(
                            navController = navController,
                            viewModel = detailViewModel,
                            id = recipeId!!
                        )
                    }

                }

            }
        }
    }
}

