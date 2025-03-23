// MainActivity.kt
package com.example.recipesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeSearchScreen()
        }
    }
}

@Composable
fun RecipeSearchScreen(viewModel: RecipeViewModel = remember { RecipeViewModel() }) {
    var query by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Recipe") }
        )
        Button(onClick = { viewModel.searchRecipes(query) }) {
            Text("Search")
        }

        when (uiState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> RecipeList((uiState as UiState.Success).recipes)
            is UiState.Error -> Text("Error: ${(uiState as UiState.Error).message}")
            UiState.Initial -> {}
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipes) { recipe ->
            RecipeItem(recipe)
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Row(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(recipe.label, style = MaterialTheme.typography.bodyLarge)
            Text(recipe.source, style = MaterialTheme.typography.bodySmall)
        }
    }
}




