package de.rogallab.android.domain.usecases

sealed class UiState {
   object Empty: UiState();
   object Loading : UiState()
   object Retrying : UiState()
   data class Success<T>(val data: T) : UiState()
   data class Error(val message: String) : UiState()
}




