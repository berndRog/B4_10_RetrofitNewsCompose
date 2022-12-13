package de.rogallab.android.domain.usecases

import de.rogallab.android.domain.ILogger
import de.rogallab.android.domain.INewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class ReadArticleById @Inject constructor(
   private val _newsRepository: INewsRepository,
   private val _dispatcher: CoroutineDispatcher,
   private val _logger: ILogger
) {
   init { _logger.d(tag, "init()") }

   operator fun invoke(id: UUID): Flow<UiState> = flow {

      _logger.d(tag, "invoke() emit loading")
      emit(UiState.Loading)

      _logger.d(tag, "invoke() emit success")
      val task = _newsRepository.readById(id)
      emit(UiState.Success(data = task))

   }.catch {
      _logger.d(tag, "invoke() emit error")
      emit(UiState.Error(message = "${it.localizedMessage}"))
   }.flowOn(_dispatcher)

   companion object {               // 12345678901234567890123
      private const val tag: String = "ok>ReadArticleByIdUC   "
   }
}
