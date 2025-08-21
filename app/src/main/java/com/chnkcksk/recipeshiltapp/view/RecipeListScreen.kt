package com.chnkcksk.recipeshiltapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

            SearchBar(
                hint = "Search..",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchRecipeList(it)
            }

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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    var isHintDisplayed by remember { mutableStateOf(hint != "") }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it) // Her karakter değişiminde ViewModel'e bildirim gönder
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    // Focus durumuna göre hint text'in görünürlüğünü ayarla
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }

        )
        // Placeholder text gösterimi
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }

}