package de.rogallab.android.domain.usecases

import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.INewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ReadAllArticles @Inject constructor(
   private val _newsRepository: INewsRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init { _logger.d(tag, "init()") }

   operator fun invoke(): Flow<UiState> = flow {

      _logger.d(tag, "invoke() emit loading")
      emit(UiState.Loading)


//    _newsRepository.readAllArticles().distinctUntilChanged().collect {
      _newsRepository.readAllArticles().collect {
//       throw Exception("Error readAllArticles()")
         _logger.d(tag, "invoke() emit success")
         emit(UiState.Success(data = it))
      }
   }  .catch {
         _logger.d(tag, "invoke() emit error")
         emit(UiState.Error(message = "${it.localizedMessage}"))
      }
      .flowOn(_dispatcher)

   companion object {               // 12345678901234567890123
      private const val tag: String = "ok>ReadAllArticlesUC   "
   }
}
