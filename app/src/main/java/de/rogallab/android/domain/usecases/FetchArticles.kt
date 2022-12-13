package de.rogallab.android.domain.usecases


import de.rogallab.android.data.Resource
import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.INewsRepository
import de.rogallab.android.domain.dtos.News
import de.rogallab.android.domain.entities.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchArticles @Inject constructor(
   private val _newsRepository: INewsRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {

   init {
      _logger.d(tag, "init()")
   }

   operator fun invoke(searchText: String, page: Int): Flow<UiState> = flow {

      _logger.d(tag, "invoke() emit loading")
      emit(UiState.Loading)

      _logger.d(tag, "invoke() emit success")
      _newsRepository.getArticles(searchText, page).collect { resource: Resource<News> ->

         when (resource) {
            is Resource.Success -> {
               val news: News? = resource.data
               val articles: List<Article> = news?.let {
                  it.status
                  it.totalResults
                  it.articles
               } ?: listOf<Article>()
               emit(UiState.Success(data = articles))
            }
            is Resource.Error -> {
               val message = resource.message ?: "Error in retrofit"
               emit(UiState.Error(message = message))
            }
         }
      }
   }  .catch {
         _logger.d(tag, "invoke() emit error")
         emit(UiState.Error(message = "${it.localizedMessage}"))
      }
      .flowOn(_dispatcher)

   companion object {               // 12345678901234567890123
      private const val tag: String = "ok>FetchArticlesUC     "
   }
}