package com.chnkcksk.recipeshiltapp.view

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.chnkcksk.recipeshiltapp.model.Recipe
import com.chnkcksk.recipeshiltapp.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    viewModel: RecipeDetailViewModel,
    id: Int
) {

    LaunchedEffect(id) {
        viewModel.loadRecipeById(id)
    }


    val recipe by remember { viewModel.recipe }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            errorMessage.isNotEmpty() -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error: $errorMessage"
                )
            }

            recipe != null -> {

                RecipeDetails(
                    r = recipe!!,
                    navController = navController
                )

            }

        }

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeDetails(r: Recipe, navController: NavController) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Button",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = r.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))


        Image(
            painter = rememberImagePainter(data = r.image),
            contentDescription = r.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(12.dp))
        RatingBar(
            rating = r.rating,
            reviewCount = r.reviewCount,
            modifier = Modifier.align(Alignment.CenterHorizontally) // sola hizalı; istersen CenterHorizontally ile ortala
        )

        Spacer(modifier = Modifier.height(16.dp))


        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                InfoItem("Prep", "${r.prepTimeMinutes} min")
                InfoItem("Cook", "${r.cookTimeMinutes} min")
                InfoItem("Servings", "${r.servings}")
                InfoItem("Calories", "${r.caloriesPerServing}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("Ingredients")
        r.ingredients.forEach {
            Text(text = "• $it", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("Instructions")
        r.instructions.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("Details")
        Text("Difficulty: ${r.difficulty}")
        Text("Cuisine: ${r.cuisine}")
        Text("Rating: ${r.rating} (${r.reviewCount} reviews)")
        Text("User ID: ${r.userId}")

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("Tags")
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            r.tags.forEach {
                AssistChip(onClick = {}, label = { Text(it) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        SectionTitle("Meal Type")
        r.mealType.forEach {
            Text(text = "• $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun InfoItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}


@Composable
fun RatingBar(
    rating: Double,
    reviewCount: Int? = null,
    modifier: Modifier = Modifier,
    starSize: Dp = 20.dp,
    spacing: Dp = 4.dp,
    showValue: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        val roundedToHalf = (rating * 2).toInt() / 2.0

        for (i in 1..5) {
            val icon = when {
                roundedToHalf >= i -> Icons.Rounded.Star
                roundedToHalf >= i - 0.5 -> Icons.Rounded.StarHalf
                else -> Icons.Rounded.StarOutline
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(starSize)
            )
            if (i != 5) Spacer(Modifier.width(spacing))
        }

        if (showValue) {
            Spacer(Modifier.width(8.dp))
            Text(
                text = String.format("%.1f", rating) + (reviewCount?.let { " ($it)" } ?: ""),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}