// RecipeViewModel.kt
package com.example.recipesearchapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = ApiClient.apiService.getRecipes(
                    query = query,
                    appId = "APPID",
                    appKey = "APPKEY",
                    userId = "USERID"
                )
                _uiState.value = UiState.Success(response.hits.map { it.recipe })
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

}

sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    data class Success(val recipes: List<Recipe>) : UiState()
    data class Error(val message: String) : UiState()
}