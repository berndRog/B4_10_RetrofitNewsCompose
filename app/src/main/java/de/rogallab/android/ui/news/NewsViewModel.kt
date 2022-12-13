package de.rogallab.android.ui.news

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.entities.Article
import de.rogallab.android.domain.INewsRepository
import de.rogallab.android.domain.INewsUseCases
import de.rogallab.android.domain.usecases.UiState
import de.rogallab.android.ui.navigation.NavScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.InvalidObjectException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
   private val _context: Context,
   private val _newsUseCases: INewsUseCases,
   private val _repository: INewsRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) : ViewModel() {

   var headlinesPage: Int = 1
   var articlesPage: Int = 1
   var urlPath:String = ""

   var article: Article? = null

   // SearchArticles
   var searchText: String by mutableStateOf(value = "")
      private set;
   fun onSearchTextChange(value: String) {
      searchText = value
   }
   var startSearchArticles: Boolean  by mutableStateOf(value = false)
      private set
   fun onStartSearchArticlesChange(value: Boolean) {
      startSearchArticles = value
   }

   // error handling
   var errorMessage: String? by mutableStateOf(value = null)
      private set
   fun onErrorChange(value: String?) {
      errorMessage = value
   }
   fun onErrorDismiss() {
      _logger.d(tag, "onErrorDismiss()")
   }
   fun onErrorAction() {
      _logger.d(tag, "onErrorAction()")

   }

   var stateHeadlines: StateFlow<UiState> = MutableStateFlow(UiState.Empty)
      private set
   var stateArticles: StateFlow<UiState> = MutableStateFlow(UiState.Empty)
      private set
   var stateSavedArticles: StateFlow<UiState> = MutableStateFlow(UiState.Empty)

   init {
      //_logger.d(tag,"init(), readHeadlines()")
      //readHeadlines("de")
   }

   fun readHeadlines(country: String): Unit {
      headlinesPage = 1
      stateHeadlines = flow {
         _logger.d(tag, "searchHeadlines() $country  page $headlinesPage")
         _newsUseCases.fetchHeadlines(country, headlinesPage).collect{ uiState ->
            emit(uiState)
         }
      }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Empty)
   }


   fun searchArticles(text: String?): Unit {
      text?.let {
         searchText = text
      } ?: run{
         searchText=""
      }
      articlesPage = 1
      stateArticles = flow {
         _logger.d(tag, "searchArticles() $searchText page $articlesPage")
         _newsUseCases.fetchArticles(searchText, articlesPage).collect { uiState ->
            emit(uiState)
         }
      }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Empty)
   }

   fun readSavedArticles() {
      stateSavedArticles = flow {
         _logger.d(tag, "readSavedArticles()")
         _newsUseCases.readAllArticles().collect { uiState ->
            emit(uiState)
         }
      }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Empty)
   }

   fun upsert() = viewModelScope.launch(_dispatcher) {
      _logger.d(tag, "upsert()")
      try {
         article?.let {
            _repository.upsert(it)
         } ?: run {
            throw InvalidObjectException("Kann den Artikel nicht speichern")
         }
      }
      catch (e: Exception) {
         onErrorChange("Error in NewsViewModel.upsert(): ${e.localizedMessage}")
      }
   }

   fun remove() = viewModelScope.launch(_dispatcher) {
      _logger.d(tag, "remove()")
      try {
         article?.let {
            _repository.remove(it)
         } ?: run {
            throw InvalidObjectException("Kann den Artikel nicht löschen")
         }
      }
      catch (e: Exception) {
         onErrorChange("Error in NewsViewModel.upsert(): ${e.localizedMessage}")
      }
   }

   companion object {
                            // 12345678901234567890123
      private const val tag = "ok>NewsViewModel       "
   }
}