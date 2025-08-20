package com.chnkcksk.recipeshiltapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.chnkcksk.recipeshiltapp.viewmodel.RecipeListViewModel


@Composable
fun RecipeListScreen(
    navController: NavController, viewModel: RecipeListViewModel
) {

    val recipeList by remember { viewModel.recipeList }
    val isLoading by remember { viewModel.isLoading }
    val errorMessage by remember { viewModel.errorMessage }

    println("recipeList: $recipeList")

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {

            Text(
                text = "Recipes",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxSize()) {

                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    errorMessage.isNotEmpty() -> {
                        Text(
                            text = "Error: $errorMessage",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    recipeList.isNotEmpty() -> {
                        Column {
                            LazyColumn {
                                items(recipeList) { recipe ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clickable {
                                                    navController.navigate(
                                                        "recipe_detail_screen/${recipe.id}"
                                                    )
                                            }
                                    ) {
                                        Row {
                                            Image(
                                                painter = rememberImagePainter(data = recipe.image),
                                                contentDescription = recipe.name,
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .size(100.dp)
                                                    .border(2.dp, Color.Gray),
                                            )
                                            Column(modifier = Modifier.padding(16.dp)) {
                                                Text(
                                                    text = "${recipe.name}",
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    fontWeight = FontWeight.Bold
                                                )

                                                recipe.ingredients.forEach {
                                                    Text(
                                                        text = it
                                                    )
                                                }


                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                }

            }


        }

    }

}

//@Preview
//@Composable
//private fun view() {
//    RecipeListScreen()
//}
